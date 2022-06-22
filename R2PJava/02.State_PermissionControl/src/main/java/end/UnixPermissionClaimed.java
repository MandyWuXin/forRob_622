package end;

public class UnixPermissionClaimed extends PermissionState{
    public UnixPermissionClaimed() {
        super("UNIX_CLAIMED");
    }

    public void deniedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getAdmin().equals(admin))
            return;
        permission.setGranted(false);
        permission.setUnixPermissionGranted(false);
        permission.setState(PermissionState.DENIED);
        permission.notifyUserOfPermissionRequestResult();
    }

    public void grantedBy(SystemAdmin admin, SystemPermission permission) {
        if (!permission.getAdmin().equals(admin))
            return;

        if (permission.getProfile().isUnixPermissionRequired() && !permission.isUnixPermissionGranted()) {
            permission.setState(PermissionState.UNIX_REQUESTED);
            permission.notifyUnixAdminsOfPermissionRequest();
            return;
        }
        permission.setState(PermissionState.GRANTED);
        permission.setGranted(true);
        permission.notifyUserOfPermissionRequestResult();
    }
}
