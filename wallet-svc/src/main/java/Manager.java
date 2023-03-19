import com.lwl.wallet.service.*;

public class Manager {
    public static void main(String[] args) {
        AppUserService appUserService = new AppUserServiceImpl();
        WalletService walletService = new WalletServiceImpl();
        ConvertToCsv convertToCsv = new ConvertToCsv();

        convertToCsv.toCSV();


    }
}
