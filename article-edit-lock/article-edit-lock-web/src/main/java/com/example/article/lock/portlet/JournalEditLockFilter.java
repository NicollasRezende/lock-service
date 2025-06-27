package com.example.article.lock.portlet;

import com.example.article.lock.service.ArticleEditLockLocalService;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.PrintWriter;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(immediate = true, property = {
                "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
                "service.ranking:Integer=1000"
}, service = javax.portlet.filter.PortletFilter.class)
public class JournalEditLockFilter implements RenderFilter {

        private static final Log _log = LogFactoryUtil.getLog(JournalEditLockFilter.class);

        @Override
        public void init(FilterConfig filterConfig) throws PortletException {
        }

        @Override
        public void destroy() {
        }

        @Override
        public void doFilter(
                        RenderRequest renderRequest, RenderResponse renderResponse,
                        FilterChain filterChain)
                        throws IOException, PortletException {

                String mvcRenderCommandName = ParamUtil.getString(renderRequest, "mvcRenderCommandName");
                String articleId = ParamUtil.getString(renderRequest, "articleId");

                // Verifica se está editando um artigo existente
                if ("/journal/edit_article".equals(mvcRenderCommandName) &&
                                articleId != null && !articleId.isEmpty()) {

                        try {
                                ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(
                                                WebKeys.THEME_DISPLAY);

                                ServiceContext serviceContext = ServiceContextFactory.getInstance(
                                                renderRequest);

                                // Verificar se há lock ativo no banco
                                com.example.article.lock.model.ArticleEditLock activeLock = _articleEditLockLocalService
                                                .getActiveArticleLock(articleId);

                                // Verifica se está bloqueado por outro usuário
                                boolean isLocked = _articleEditLockLocalService.isArticleLockedByOtherUser(
                                                articleId, themeDisplay.getUserId());

                                if (isLocked) {
                                        User editingUser = _articleEditLockLocalService.getEditingUser(articleId);

                                        if (editingUser != null
                                                        && editingUser.getUserId() != themeDisplay.getUserId()) {
                                                // Renderiza o modal apenas se for outro usuário
                                                renderLockModal(renderResponse, editingUser, themeDisplay, articleId);
                                                return;
                                        }

                                        // Se há lock ativo de outro usuário, deve parar aqui
                                        if (editingUser != null
                                                        && editingUser.getUserId() != themeDisplay.getUserId()) {
                                                renderLockModal(renderResponse, editingUser, themeDisplay, articleId);
                                                return;
                                        }
                                }

                                // Tenta criar/atualizar o lock para o usuário atual
                                {
                                        // Tenta criar ou atualizar o lock para o usuário atual
                                        boolean lockCreated = _articleEditLockLocalService.tryLockArticle(
                                                        articleId, themeDisplay.getUserId(), serviceContext);

                                        if (!lockCreated) {
                                                // Se falhou, verifica se é porque outro usuário está editando
                                                User editingUser = _articleEditLockLocalService
                                                                .getEditingUser(articleId);

                                                if (editingUser != null && editingUser.getUserId() != themeDisplay
                                                                .getUserId()) {
                                                        // Só mostra modal se for realmente outro usuário
                                                        renderLockModal(renderResponse, editingUser, themeDisplay,
                                                                        articleId);
                                                        return;
                                                } else {
                                                        // Mesmo que não seja outro usuário, se o lock falhou, não deve
                                                        // prosseguir
                                                        renderErrorPage(renderResponse,
                                                                        "Não foi possível obter acesso ao documento. Tente novamente.");
                                                        return;
                                                }
                                        }
                                }
                        } catch (Exception e) {
                                _log.error("Error in JournalEditLockFilter", e);
                                e.printStackTrace();
                        }
                }

                // Continua com o processamento normal se não houver bloqueio
                filterChain.doFilter(renderRequest, renderResponse);

                // Se estamos editando um artigo, injeta o script de monitoramento APÓS o
                // processamento
                if ("/journal/edit_article".equals(mvcRenderCommandName) &&
                                articleId != null && !articleId.isEmpty()) {

                        try {
                                ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest
                                                .getAttribute(WebKeys.THEME_DISPLAY);

                                // Injeta o script diretamente no final da resposta
                                injectControlMonitorScript(renderResponse, articleId, themeDisplay);

                        } catch (Exception e) {
                                _log.warn("Failed to inject control monitor script", e);
                        }
                }
        }

        private void renderLockModal(RenderResponse renderResponse, User editingUser,
                        ThemeDisplay themeDisplay, String articleId)
                        throws IOException {

                renderResponse.setContentType("text/html");
                PrintWriter writer = renderResponse.getWriter();

                String editingUserName = HtmlUtil.escape(editingUser.getFullName());
                String editingUserId = String.valueOf(editingUser.getUserId());

                writer.println("<!DOCTYPE html>");
                writer.println("<html>");
                writer.println("<head>");
                writer.println("<meta charset='UTF-8'>");
                writer.println("<link rel='stylesheet' href='" + themeDisplay.getCDNHost() +
                                "/o/frontend-theme-font-awesome-web/css/main.css'>");
                writer.println("<link rel='stylesheet' href='" + themeDisplay.getPortalURL() +
                                "/o/frontend-css-web/main.css'>");
                writer.println("<style>");
                writer.println("  .article-lock-modal { padding: 2rem; }");
                writer.println("  .modal-content-custom { padding: 2rem; }");
                writer.println("  .modal-actions { margin-top: 2rem; text-align: right; }");
                writer.println("  .btn + .btn { margin-left: 0.5rem; }");
                writer.println("  .content-text { color: #6c757d; margin-bottom: 1.5rem; }");
                writer.println("  .options-text { color: #495057; margin-bottom: 1rem; }");
                writer.println("  .options-list { margin-left: 0; padding-left: 0; }");
                writer.println("  .options-list li { margin-bottom: 0.5rem; color: #6c757d; }");
                writer.println("  .card-title { color: #495057; font-weight: 600; }");
                writer.println("</style>");
                writer.println("</head>");
                writer.println("<body>");

                writer.println("<div class='container-fluid article-lock-modal'>");
                writer.println("  <div class='row justify-content-center'>");
                writer.println("    <div class='col-md-6'>");
                writer.println("      <div class='card'>");
                writer.println("        <div class='card-body modal-content-custom'>");

                // Título principal
                writer.println("          <h3 class='card-title mb-4'>Este conteúdo está sendo editado!</h3>");

                // Texto explicativo principal
                writer.println("          <p class='content-text'>Este documento está sendo editado por outro membro da equipe neste momento.</p>");

                // Texto das opções
                writer.println("          <p class='options-text'>Para manter a colaboração organizada, você pode:</p>");

                // Lista de opções
                writer.println("          <ul class='options-list'>");
                writer.println("            <li>Assumir o controle, encerrando a edição atual do outro colaborador.</li>");
                writer.println("            <li>Voltar, e aguardar a finalização da edição em andamento.</li>");
                writer.println("          </ul>");

                // Botões de ação
                writer.println("          <div class='modal-actions'>");

                // Botão Voltar
                writer.println("            <button type='button' class='btn btn-secondary' " +
                                "onclick='goBackSafely();'>");
                writer.println("              <span class='lfr-btn-label'>Voltar</span>");
                writer.println("            </button>");

                // Botão Assumir o controle
                writer.println("            <button type='button' class='btn btn-primary' " +
                                "id='takeControlBtn' " +
                                "data-article-id='" + HtmlUtil.escapeAttribute(articleId) + "'>");
                writer.println("              <span class='lfr-btn-label'>Assumir o controle</span>");
                writer.println("            </button>");

                writer.println("          </div>");
                writer.println("        </div>");
                writer.println("      </div>");
                writer.println("    </div>");
                writer.println("  </div>");
                writer.println("</div>");

                // JavaScript
                writer.println("<script>");
                writer.println("// Função segura para voltar que evita interferir com o sistema de lock");
                writer.println("function goBackSafely() {");
                writer.println("  // Redireciona diretamente para a lista de artigos");
                writer.println("  var listUrl = window.location.origin + window.location.pathname +");
                writer.println("                '?p_p_id=" + JournalPortletKeys.JOURNAL + "&p_p_lifecycle=0' +");
                writer.println("                '&_" + JournalPortletKeys.JOURNAL
                                + "_mvcRenderCommandName=%2Fjournal%2Fview';");
                writer.println("  window.location.href = listUrl;");
                writer.println("}");
                writer.println("");
                writer.println("(function() {");

                // Script do botão Tomar Controle
                writer.println("  var takeControlBtn = document.getElementById('takeControlBtn');");
                writer.println("  if (takeControlBtn) {");
                writer.println("    takeControlBtn.addEventListener('click', function(e) {");
                writer.println("      e.preventDefault();");
                writer.println("      ");
                writer.println("      var articleId = this.getAttribute('data-article-id');");
                writer.println("      console.log('Taking control of article:', articleId);");
                writer.println("      ");
                writer.println("      var confirmMsg = 'Tem certeza que deseja assumir o controle deste documento?';");
                writer.println("      ");
                writer.println("      if (confirm(confirmMsg)) {");
                writer.println("        var form = document.createElement('form');");
                writer.println("        form.method = 'POST';");
                writer.println("        form.action = '" + renderResponse.createActionURL() + "';");
                writer.println("        ");

                // Adicionar parâmetros do portlet namespace
                writer.println("        var namespaceInput = document.createElement('input');");
                writer.println("        namespaceInput.type = 'hidden';");
                writer.println("        namespaceInput.name = '" + renderResponse.getNamespace()
                                + "javax.portlet.action';");
                writer.println("        namespaceInput.value = '/journal/take_article_control';");
                writer.println("        form.appendChild(namespaceInput);");
                writer.println("        ");

                // Adicionar articleId com namespace
                writer.println("        var articleInput = document.createElement('input');");
                writer.println("        articleInput.type = 'hidden';");
                writer.println("        articleInput.name = '" + renderResponse.getNamespace() + "articleId';");
                writer.println("        articleInput.value = articleId;");
                writer.println("        form.appendChild(articleInput);");
                writer.println("        ");

                // Adicionar parâmetros do lifecycle
                writer.println("        var lifecycleInput = document.createElement('input');");
                writer.println("        lifecycleInput.type = 'hidden';");
                writer.println("        lifecycleInput.name = 'p_p_id';");
                writer.println("        lifecycleInput.value = '"
                                + renderResponse.getNamespace().replace("_INSTANCE_", "").replace("_", "") + "';");
                writer.println("        form.appendChild(lifecycleInput);");
                writer.println("        ");

                writer.println("        var lifecycleInput2 = document.createElement('input');");
                writer.println("        lifecycleInput2.type = 'hidden';");
                writer.println("        lifecycleInput2.name = 'p_p_lifecycle';");
                writer.println("        lifecycleInput2.value = '1';");
                writer.println("        form.appendChild(lifecycleInput2);");
                writer.println("        ");

                // Criar URL de redirect para edição
                String editUrl = themeDisplay.getPortalURL() +
                                themeDisplay.getURLCurrent() +
                                "?p_p_id=" + JournalPortletKeys.JOURNAL +
                                "&p_p_lifecycle=0" +
                                "&_" + JournalPortletKeys.JOURNAL +
                                "_mvcRenderCommandName=/journal/edit_article" +
                                "&_" + JournalPortletKeys.JOURNAL +
                                "_articleId=" + articleId;

                writer.println("        var redirectInput = document.createElement('input');");
                writer.println("        redirectInput.type = 'hidden';");
                writer.println("        redirectInput.name = '" + renderResponse.getNamespace() + "redirect';");
                writer.println("        redirectInput.value = '" +
                                HtmlUtil.escapeJS(editUrl) + "';");
                writer.println("        form.appendChild(redirectInput);");
                writer.println("        ");
                writer.println("        var backURLInput = document.createElement('input');");
                writer.println("        backURLInput.type = 'hidden';");
                writer.println("        backURLInput.name = '" + renderResponse.getNamespace() + "backURL';");
                writer.println("        backURLInput.value = window.location.href;");
                writer.println("        form.appendChild(backURLInput);");
                writer.println("        ");
                writer.println("        console.log('Submitting form with articleId:', articleId);");
                writer.println("        document.body.appendChild(form);");
                writer.println("        form.submit();");
                writer.println("      }");
                writer.println("    });");
                writer.println("  }");

                writer.println("})();");
                writer.println("</script>");

                // Script de monitoramento de controle
                writer.println("<script>");
                writer.println("// Monitor de controle de artigo - Atualiza página se perder controle");
                writer.println("(function() {");
                writer.println("  var articleId = '" + HtmlUtil.escapeJS(articleId) + "';");
                writer.println("  var userId = " + themeDisplay.getUserId() + ";");
                writer.println("  var checkInterval;");
                writer.println("  ");
                writer.println("  function checkArticleControl() {");
                writer.println("    var xhr = new XMLHttpRequest();");
                writer.println("    var url = '" + themeDisplay.getPortalURL() + "/web"
                                + themeDisplay.getScopeGroup().getFriendlyURL() + "/manage" +
                                "?p_p_id=" + JournalPortletKeys.JOURNAL +
                                "&p_p_lifecycle=2" +
                                "&p_p_resource_id=%2Fjournal%2Fcheck_article_lock" +
                                "&_" + JournalPortletKeys.JOURNAL + "_articleId=' + encodeURIComponent(articleId);");
                writer.println("    ");
                writer.println("    xhr.open('GET', url, true);");
                writer.println("    xhr.setRequestHeader('Content-Type', 'application/json');");
                writer.println("    ");
                writer.println("    xhr.onreadystatechange = function() {");
                writer.println("      if (xhr.readyState === 4 && xhr.status === 200) {");
                writer.println("        try {");
                writer.println("          var response = JSON.parse(xhr.responseText);");
                writer.println("          ");
                writer.println("          if (response.success && !response.hasControl) {");
                writer.println("            console.log('Controle perdido! Usuário atual:', response.currentEditor);");
                writer.println("            ");
                writer.println("            // Para o monitoramento");
                writer.println("            clearInterval(checkInterval);");
                writer.println("            ");
                writer.println("            // Redireciona silenciosamente - usuário já sabe que não tem controle (está vendo o modal)");
                writer.println("            // Redireciona para a lista de artigos");
                writer.println("            var listUrl = window.location.origin + window.location.pathname +");
                writer.println("                          '?p_p_id=" + JournalPortletKeys.JOURNAL + "' +");
                writer.println("                          '&p_p_lifecycle=0' +");
                writer.println("                          '&_" + JournalPortletKeys.JOURNAL
                                + "_mvcRenderCommandName=%2Fjournal%2Fview';");
                writer.println("            ");
                writer.println("            window.location.href = listUrl;");
                writer.println("          }");
                writer.println("        } catch (e) {");
                writer.println("          console.error('Erro ao verificar controle do artigo:', e);");
                writer.println("        }");
                writer.println("      }");
                writer.println("    };");
                writer.println("    ");
                writer.println("    xhr.send();");
                writer.println("  }");
                writer.println("  ");
                writer.println("  // NÃO inicia monitoramento no modal - usuário já sabe que não tem controle");
                writer.println("  console.log('ℹ️ Modal de controle - monitoramento desnecessário');");
                writer.println("})();");
                writer.println("</script>");

                writer.println("</body>");
                writer.println("</html>");
        }

        private void injectControlMonitorScript(RenderResponse renderResponse, String articleId,
                        ThemeDisplay themeDisplay) throws IOException {

                String script = String.format(
                                "<script>\n" +
                                                "console.log('>>> Article lock monitor injected for article: %s');\n" +
                                                "(function() {\n" +
                                                "  var articleId = '%s';\n" +
                                                "  var userId = %d;\n" +
                                                "  var checkInterval;\n" +
                                                "  \n" +
                                                "  function checkArticleControl() {\n" +
                                                "    var xhr = new XMLHttpRequest();\n" +
                                                "    var url = '%s/web%s/manage?p_p_id=%s&p_p_lifecycle=2&p_p_resource_id=%%2Fjournal%%2Fcheck_article_lock&_%s_articleId=' + encodeURIComponent(articleId);\n"
                                                +
                                                "    \n" +
                                                "    xhr.open('GET', url, true);\n" +
                                                "    xhr.setRequestHeader('Content-Type', 'application/json');\n" +
                                                "    \n" +
                                                "    xhr.onreadystatechange = function() {\n" +
                                                "      if (xhr.readyState === 4) {\n" +
                                                "        console.log('📡 Response Status:', xhr.status);\n" +
                                                "        console.log('📡 Response Text:', xhr.responseText);\n" +
                                                "        \n" +
                                                "        if (xhr.status === 200) {\n" +
                                                "          try {\n" +
                                                "            var response = JSON.parse(xhr.responseText);\n" +
                                                "            console.log('📊 Parsed Response:', response);\n" +
                                                "            \n" +
                                                "            if (response.success) {\n" +
                                                "              if (response.lockExists) {\n" +
                                                "                console.log('🔒 Lock existe no banco!');\n" +
                                                "                console.log('- Editor atual ID:', response.currentEditorId);\n"
                                                +
                                                "                console.log('- Editor atual nome:', response.currentEditor);\n"
                                                +
                                                "                console.log('- Meu ID:', response.userId);\n" +
                                                "                console.log('- Tenho controle?', response.hasControl);\n"
                                                +
                                                "                \n" +
                                                "                if (!response.hasControl) {\n" +
                                                "                  console.log('⚠️ CONTROLE PERDIDO! Editor atual:', response.currentEditor);\n"
                                                +
                                                "                  \n" +
                                                "                  clearInterval(checkInterval);\n" +
                                                "                  \n" +
                                                "                  var editorName = response.currentEditor || 'Outro usuário';\n"
                                                +
                                                "                  \n" +
                                                "                  // Usar toast do Liferay em vez de alert\n" +
                                                "                  if (typeof Liferay !== 'undefined' && Liferay.Util && Liferay.Util.openToast) {\n"
                                                +
                                                "                    Liferay.Util.openToast({\n" +
                                                "                      message: '⚠️ ' + editorName + ' assumiu o controle deste documento. Redirecionando...',\n"
                                                +
                                                "                      title: 'Controle Transferido',\n" +
                                                "                      type: 'warning',\n" +
                                                "                      autoClose: 5000\n" +
                                                "                    });\n" +
                                                "                  } else {\n" +
                                                "                    // Fallback para alert padrão\n" +
                                                "                    alert('⚠️ CONTROLE TRANSFERIDO\\n\\n' + editorName + ' assumiu o controle deste documento.\\nA página será redirecionada automaticamente.');\n"
                                                +
                                                "                  }\n" +
                                                "                  \n" +
                                                "                  // Aguarda um pouco antes de redirecionar para que o toast apareça\n"
                                                +
                                                "                  setTimeout(function() {\n" +
                                                "                    var listUrl = window.location.origin + window.location.pathname +\n"
                                                +
                                                "                                  '?p_p_id=%s&p_p_lifecycle=0&_%s_mvcRenderCommandName=%%2Fjournal%%2Fview';\n"
                                                +
                                                "                    window.location.href = listUrl;\n" +
                                                "                  }, 2500);\n" +
                                                "                } else {\n" +
                                                "                  console.log('✅ Ainda tenho controle do artigo');\n" +
                                                "                }\n" +
                                                "              } else {\n" +
                                                "                console.log('🔓 Nenhum lock no banco - artigo livre');\n"
                                                +
                                                "              }\n" +
                                                "            } else {\n" +
                                                "              console.error('❌ Resposta indica falha:', response.message);\n"
                                                +
                                                "            }\n" +
                                                "          } catch (e) {\n" +
                                                "            console.error('❌ Erro ao parsear JSON:', e);\n" +
                                                "            console.error('Response text was:', xhr.responseText);\n" +
                                                "          }\n" +
                                                "        } else {\n" +
                                                "          console.error('❌ HTTP Error:', xhr.status, xhr.statusText);\n"
                                                +
                                                "        }\n" +
                                                "      }\n" +
                                                "    };\n" +
                                                "    \n" +
                                                "    xhr.send();\n" +
                                                "  }\n" +
                                                "  \n" +
                                                "  // Verifica se está editando um artigo\n" +
                                                "  if (articleId && (window.location.href.indexOf('edit_article') > -1 || window.location.href.indexOf('articleId') > -1)) {\n"
                                                +
                                                "    console.log('🔄 INICIANDO monitoramento de controle para artigo:', articleId);\n"
                                                +
                                                "    \n" +
                                                "    // Verifica a cada 3 segundos\n" +
                                                "    checkInterval = setInterval(checkArticleControl, 3000);\n" +
                                                "    \n" +
                                                "    // Primeira verificação em 2 segundos\n" +
                                                "    setTimeout(checkArticleControl, 2000);\n" +
                                                "    \n" +
                                                "    // Para o monitoramento quando a página for fechada\n" +
                                                "    window.addEventListener('beforeunload', function() {\n" +
                                                "      if (checkInterval) {\n" +
                                                "        clearInterval(checkInterval);\n" +
                                                "        console.log('🛑 Monitoramento de controle parado');\n" +
                                                "      }\n" +
                                                "    });\n" +
                                                "  } else {\n" +
                                                "    console.log('ℹ️ Não está editando artigo, monitoramento não iniciado');\n"
                                                +
                                                "  }\n" +
                                                "})();\n" +
                                                "</script>",
                                HtmlUtil.escapeJS(articleId),
                                HtmlUtil.escapeJS(articleId),
                                themeDisplay.getUserId(),
                                themeDisplay.getPortalURL(),
                                themeDisplay.getScopeGroup().getFriendlyURL(),
                                JournalPortletKeys.JOURNAL,
                                JournalPortletKeys.JOURNAL,
                                JournalPortletKeys.JOURNAL,
                                JournalPortletKeys.JOURNAL);

                renderResponse.getWriter().write(script);
        }

        private void renderErrorPage(RenderResponse renderResponse, String errorMessage) throws IOException {
                renderResponse.setContentType("text/html");
                PrintWriter writer = renderResponse.getWriter();

                writer.println("<!DOCTYPE html>");
                writer.println("<html>");
                writer.println("<head>");
                writer.println("<meta charset='UTF-8'>");
                writer.println("<title>Erro de Acesso</title>");
                writer.println("<style>");
                writer.println("  .error-container { padding: 2rem; text-align: center; }");
                writer.println("  .error-message { color: #dc3545; margin-bottom: 1rem; }");
                writer.println("  .btn { padding: 0.5rem 1rem; background: #007bff; color: white; text-decoration: none; border-radius: 4px; }");
                writer.println("</style>");
                writer.println("</head>");
                writer.println("<body>");
                writer.println("<div class='error-container'>");
                writer.println("  <h3>Erro de Acesso</h3>");
                writer.println("  <p class='error-message'>" + HtmlUtil.escape(errorMessage) + "</p>");
                writer.println("  <a href='javascript:history.back()' class='btn'>Voltar</a>");
                writer.println("</div>");
                writer.println("</body>");
                writer.println("</html>");
        }

        @Reference
        private ArticleEditLockLocalService _articleEditLockLocalService;
}