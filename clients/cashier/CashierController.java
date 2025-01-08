package clients.cashier;

import clients.customer.NameToNumber;

/**
 * The Cashier Controller
 */

public class CashierController
{
  private CashierModel model = null;
  private CashierView  view  = null;

  /**
   * Constructor
   * @param model The model 
   * @param view  The view from which the interaction came
   */
  public CashierController( CashierModel model, CashierView view )
  {
    this.view  = view;
    this.model = model;
  }

  /**
   * Check interaction from view
   * @param pn The product number to be checked
   */
  public void doCheck( String pn, int buyQuantity )
  {
    model.doCheck(pn, buyQuantity);
  }
  
  public void doCheckByName( String name, int buyQuantity )
  {
	  NameToNumber nameToNumber = new NameToNumber();
	  String pn = nameToNumber.getNumberByName(nameToNumber, name);
	  model.doCheck(pn, buyQuantity);
  }

   /**
   * Buy interaction from view
   */
  public void doBuy()
  {
    model.doBuy();
  }
  
   /**
   * Bought interaction from view
   */
  public void doBought()
  {
    model.doBought();
  }
}
