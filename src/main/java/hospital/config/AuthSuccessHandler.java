package hospital.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
        @Override
        protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
            // Get the role of logged in user
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String role = auth.getAuthorities().toString();
            if (role.contains("ROLE_DOCTOR")) return "/doctor/doctorMenu";
            if (role.contains("ROLE_ADMINISTRATOR")) return "/admin/administratorMenu";
            if (role.contains("ROLE_SECRETARY")) return "/secretary/secretaryMenu";
            return "/noPage";
        }
}
