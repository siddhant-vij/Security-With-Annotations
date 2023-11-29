package com.securityWithAnnotations;

import com.securityWithAnnotations.internal.Role;
import com.securityWithAnnotations.internal.User;

public class Main {
  public static void main(String[] args) throws Throwable {
    CompanyDataStore companyDataStore1 = new CompanyDataStore(new User("clerkR", Role.CLERK));
    CompanyDataStore companyDataStore2 = new CompanyDataStore(new User("managerRW", Role.MANAGER));
    CompanyDataStore companyDataStore3 = new CompanyDataStore(new User("supportRWD", Role.SUPPORT_ENGINEER));

    // CompanyDataStore1 -> Clerk -> READ
    PermissionsChecker.checkPermissions(companyDataStore1, "readAccountSummary");
    PermissionsChecker.checkPermissions(companyDataStore1, "readAccounts");
    // PermissionsChecker.checkPermissions(companyDataStore1, "updateAccount");
    // PermissionsChecker.checkPermissions(companyDataStore1, "deleteAccount");

    // CompanyDataStore2 -> Manager -> READ & WRITE
    PermissionsChecker.checkPermissions(companyDataStore2, "readAccountSummary");
    PermissionsChecker.checkPermissions(companyDataStore2, "readAccounts");
    PermissionsChecker.checkPermissions(companyDataStore2, "updateAccount");
    // PermissionsChecker.checkPermissions(companyDataStore2, "deleteAccount");

    // CompanyDataStore3 -> Support Engineer -> READ & WRITE & DELETE
    PermissionsChecker.checkPermissions(companyDataStore3, "readAccountSummary");
    PermissionsChecker.checkPermissions(companyDataStore3, "readAccounts");
    PermissionsChecker.checkPermissions(companyDataStore3, "updateAccount");
    PermissionsChecker.checkPermissions(companyDataStore3, "deleteAccount");
  }
}
