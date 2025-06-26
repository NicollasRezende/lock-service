<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.portal.kernel.model.User" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>

<%
User editingUser = (User) request.getAttribute("editingUser");
String lockedArticleId = (String) request.getAttribute("lockedArticleId");

String editingUserName = "";
String editingUserId = "";

if (editingUser != null) {
    editingUserName = HtmlUtil.escape(editingUser.getFullName());
    editingUserId = String.valueOf(editingUser.getUserId());
}
%>

<div class="modal fade" id="articleLockedModal" tabindex="-1" role="dialog" aria-labelledby="articleLockedModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="articleLockedModalLabel">
                    <liferay-ui:message key="article-being-edited" />
                </h5>
            </div>
            <div class="modal-body">
                <div class="alert alert-warning" role="alert">
                    <span class="alert-indicator">
                        <svg class="lexicon-icon lexicon-icon-warning-full" focusable="false" role="presentation">
                            <use href="<%= themeDisplay.getPathThemeImages() %>/clay/icons.svg#warning-full" />
                        </svg>
                    </span>
                    <div class="alert-content">
                        <strong class="lead">
                            <liferay-ui:message key="article-locked-title" />
                        </strong>
                        <br />
                        <p>
                            <liferay-ui:message
                                arguments='<%= new String[] {editingUserName, editingUserId} %>'
                                key="article-is-being-edited-by-user"
                            />
                        </p>
                        <p>
                            <liferay-ui:message key="article-lock-timeout-message" />
                        </p>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" onclick="window.history.back();">
                    <liferay-ui:message key="go-back" />
                </button>
                
                <portlet:actionURL name="/journal/take_article_control" var="takeControlURL">
                    <portlet:param name="articleId" value="<%= lockedArticleId %>" />
                    <portlet:param name="redirect" value="<%= currentURL %>" />
                </portlet:actionURL>
                
                <button type="button" class="btn btn-warning" onclick="takeArticleControl();">
                    <liferay-ui:message key="take-control" />
                </button>
            </div>
        </div>
    </div>
</div>

<aui:script>
    $(document).ready(function() {
        $('#articleLockedModal').modal({
            backdrop: 'static',
            keyboard: false,
            show: true
        });
    });
    
    function takeArticleControl() {
        if (confirm('<liferay-ui:message key="confirm-take-control-message" />')) {
            window.location.href = '<%= takeControlURL %>';
        }
    }
</aui:script>