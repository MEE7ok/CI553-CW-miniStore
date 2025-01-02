package catalogue;

import java.io.Serializable;
import java.util.Collections;

/**
 * Write a description of class BetterBasket here.
 * 
 * @author  Artur 
 * @version 1.0
 */
public class BetterBasket extends Basket 
{
  @Override
  public boolean add( Product pr )
  {
	  for(Product prInList: this) {
		 if(prInList.getProductNum().equals(pr.getProductNum())) {
			 int quantity = pr.getQuantity()+prInList.getQuantity(); // Adding the quantity of the product plus the prInList quantity
			 prInList.setQuantity(quantity); // Setting the new quantity to the new int quantity
			 return (true); // Returning the new quantity value
		 }
	  }
	 super.add(pr); // Call Add in arrayList
	 Collections.sort(this, new SortByNum()); // Sorting the products
	 return (true);
  }
  
}
