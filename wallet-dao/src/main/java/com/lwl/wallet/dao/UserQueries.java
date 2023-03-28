package com.lwl.wallet.dao;

public class UserQueries {

    public static final String ADD_USER=
            """
                insert into appuser(username,password,email,mobile,status) values(?,?,?,?,?);
                
            """;
    public static final String ADD_WALLET=
            """
                insert into wallet(mobile,balance) values(?,?);
                    """;
    public static final String GET_BALANCE=
            """
                select balance from appuser,wallet where (appuser.mobile=wallet.mobile) and appuser.mobile=?; 
                    """;
    public static final String GET_USERS=
            """
                 select id,username, password, email, mobile, status from appuser;
                    """;
    public static final String GET_MOBILE=
            """
                 select mobile from appuser;
                    """;

    public static final String DELETE_APPUSER=
            """
                    delete from appuser where mobile=?;
                    """;
    public static final String DELETE_WALLET=
            """
                    delete from wallet where mobile=?;
                    """;

    public static final String ADD_AMOUNT_MOBILE=
            """
                update wallet set balance =? where mobile=?;
                    """;
}

