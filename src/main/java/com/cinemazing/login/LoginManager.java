package com.cinemazing.login;

import com.cinemazing.exceptions.CouponSystemException;
import com.cinemazing.facade.AdminFacade;
import com.cinemazing.facade.ClientFacade;
import com.cinemazing.facade.CompanyFacade;
import com.cinemazing.facade.CustomerFacade;

public class LoginManager {

  private static LoginManager instance = null;

  public static LoginManager getInstance() {
    if (instance == null) {
      synchronized (LoginManager.class) {
        if (instance == null) {
          instance = new LoginManager();
        }
      }
    }
    return instance;
  }

  private LoginManager() {

  }

  public ClientFacade login(String email, String password, ClientType clientType) throws CouponSystemException {
    ClientFacade clientFacade;
    switch (clientType) {
      case ADMINISTRATOR:
        clientFacade = new AdminFacade();
        if (clientFacade.login(email, password)) {
          return clientFacade;
        }
      case COMPANY:
        clientFacade = new CompanyFacade();
        if (clientFacade.login(email, password)) {
          return clientFacade;
        }
      case CUSTOMER:
        clientFacade = new CustomerFacade();
        if (clientFacade.login(email, password)) {
          return clientFacade;
        }
      default:
        throw new CouponSystemException("Login failed");
    }
  }
}
