<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="snauth" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<snauth:includes/>

<div>
    <div id="login" class="auth-tab">
            <div class="social-login-label">Вход через:</div>
            <div class="social-login">
                <a class="vk" href="https://oauth.vk.com/authorize?client_id=6648071&scope=email&redirect_uri=http://localhost:9090/vk/login&v=5.27">VKontakte</a>
                <a class="fb" href="https://www.facebook.com/dialog/oauth?redirect_uri=http://localhost:9090/facebook/login&response_type=code&client_id=465879537219967&scope=email">Facebook</a>
                <a class="gpl" href="https://accounts.google.com/o/oauth2/auth?redirect_uri=http://localhost:9090/google/login&response_type=code&client_id=621593374363-lrdruod01l8v03kf03b370j9d3tki6l7.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile">Google</a>
            </div>
    </div>
    <div id="sigup" class="auth-tab">
            <div class="social-signup-label">Регистрация через:</div>
            <div class="social-signup">
                <a class="vk" href="https://oauth.vk.com/authorize?client_id=6648071&scope=email&redirect_uri=http://localhost:9090/vk/reg&v=5.27">VKontakte</a>
                <a class="fb" href="https://www.facebook.com/dialog/oauth?redirect_uri=http://localhost:9090/facebook/reg&response_type=code&client_id=465879537219967&scope=email">Facebook</a>
                <a class="gpl" href="https://accounts.google.com/o/oauth2/auth?redirect_uri=http://localhost:9090/google/reg&response_type=code&client_id=621593374363-lrdruod01l8v03kf03b370j9d3tki6l7.apps.googleusercontent.com&scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile">Google</a>
            </div>
    </div>
</div>
<div class="popup-overlay"></div>

<div id="error-field" class="red"></div>
</body>
</html>