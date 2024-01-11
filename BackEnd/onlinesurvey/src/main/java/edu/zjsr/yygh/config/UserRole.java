package edu.zjsr.yygh.config;

public enum UserRole {
    ROLE_SYS_ADMIN("admin", 100000),
    ROLE_doc_NORMAL("user", 300000),
    ROLE_NORMAL("doctor", 200000);

    private final String roleName;

    private final Integer roleCode;

    UserRole(String roleName, Integer roleCode) {
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public Integer getRoleCode() {
        return roleCode;
    }

    public static String getRoleNameByRoleCode(Integer roleCode) {
        for (UserRole enums : UserRole.values()) {
            if(enums.getRoleCode().equals(roleCode)) {
                return enums.getRoleName();
            }
        }
        return null;
    }
}
