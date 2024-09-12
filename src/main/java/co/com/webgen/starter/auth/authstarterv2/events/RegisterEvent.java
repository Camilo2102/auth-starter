package co.com.webgen.starter.auth.authstarterv2.events;

public record RegisterEvent(String userId, String mail, String subject, String template){
}
