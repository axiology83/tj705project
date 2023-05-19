package kr.co.tj.userservice.dto;

//UserRole.java
public enum UserRole {
 TYPE1("user"), TYPE2("admin"), TYPE3("blocked");

 private String roleName;

 UserRole(String roleName) {
     this.roleName = roleName;
 }

 public String getRoleName() {
     return roleName;
 }
}
