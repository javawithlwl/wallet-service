import com.lwl.wallet.service.AppUserService;
import com.lwl.wallet.service.AppUserServiceImpl;
import com.lwl.wallet.service.WalletService;
import com.lwl.wallet.service.WalletServiceImpl;

public class Manager {
    public static void main(String[] args) {
        AppUserService appUserService = new AppUserServiceImpl();
        WalletService walletService = new WalletServiceImpl();
//
//        List<AppUser> auseList = appUserService.addUser();
//        for (AppUser auser:auseList){
//        System.out.println(auser.getId() +"...."+auser.getUsername()+"...."+ auser.getPassword()+"...."+auser.getMobile()+"..."+auser.getStatus());
//        }


//     walletService.addBalance("9603724457", Double.valueOf(100000));

//        System.out.println(walletService.getBalance("9685741258"));


//         System.out.println(appUserService.deleteUser("41523"));

//       List<AppUser> usersList = appUserService.getUsers();
//        for (AppUser users:usersList){
//            System.out.println(users.getId()+"...."+users.getUsername()+"...."+users.getPassword()+"...."+users.getEmail()+"...."+users.getMobile()+"...."+users.getStatus());
//        }

    }
}
