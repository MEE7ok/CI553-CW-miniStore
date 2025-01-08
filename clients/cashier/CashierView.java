package clients.cashier;

import catalogue.Basket;
import middle.MiddleFactory;
import middle.OrderProcessing;
import middle.StockReadWriter;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;


/**
 * View of the model 
 */
public class CashierView implements Observer
{
  private static final int H = 300;       // Height of window pixels
  private static final int W = 400;       // Width  of window pixels
  
  private static final String CHECK  = "Check";
  private static final String BUY    = "Buy";
  private static final String CHECKName = "Check Name";
  private static final String BOUGHT = "Bought/Pay";

  private final JLabel      pageTitle  = new JLabel();
  private final JLabel      theAction  = new JLabel();
  private final JTextField  theInput   = new JTextField();
  private final JTextField  buyQuantity = new JTextField();
  private final JTextArea   theOutput  = new JTextArea();
  private final JScrollPane theSP      = new JScrollPane();
  private final JButton     theBtCheck = new JButton( CHECK );
  private final JButton     theBtBuy   = new JButton( BUY );
  private final JButton 	theBtCheckByName = new JButton( CHECKName );
  private final JButton     theBtBought= new JButton( BOUGHT );

  private StockReadWriter theStock     = null;
  private OrderProcessing theOrder     = null;
  private CashierController cont       = null;
  
  /**
   * Construct the view
   * @param rpc   Window in which to construct
   * @param mf    Factor to deliver order and stock objects
   * @param x     x-coordinate of position of window on screen 
   * @param y     y-coordinate of position of window on screen  
   */
          
  public CashierView(  RootPaneContainer rpc,  MiddleFactory mf, int x, int y  )
  {
    try                                           // 
    {      
      theStock = mf.makeStockReadWriter();        // Database access
      theOrder = mf.makeOrderProcessing();        // Process order
    } catch ( Exception e )
    {
      System.out.println("Exception: " + e.getMessage() );
    }
    Container cp         = rpc.getContentPane();    // Content Pane
    Container rootWindow = (Container) rpc;         // Root Window
    cp.setLayout(null);                             // No layout manager
    rootWindow.setSize( W, H );                     // Size of Window
    rootWindow.setLocation( x, y );

    Font f = new Font("Monospaced",Font.PLAIN,12);  // Font f is

    pageTitle.setBounds( 110, 0 , 270, 20 );       
    pageTitle.setText( "Thank You for Shopping at MiniStrore" );                        
    cp.add( pageTitle );  
    
    theBtCheck.setBounds( 16, 25, 80, 40 );    // Check Button			
    theBtCheck.addActionListener(                   // Call back code
      e -> cont.doCheck( theInput.getText(),Integer.parseInt(buyQuantity.getText())) );
    theBtCheck.setBackground(Color.RED); 			// Adding a background colour to the button
    theBtCheck.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Adding a border to the button 
    theBtCheck.setFont(new Font("Arial", Font.BOLD, 14)); // Changing the font of the text in the button and making it bold
    theBtCheck.setContentAreaFilled(false);         // Removes the previous effects on the button
    theBtCheck.setOpaque(true);						// Adds the new changes that I have made. 
    cp.add( theBtCheck );                           //  Add to canvas
    
    theBtCheckByName.setBounds(16, 25+50, 80, 40 );
    theBtCheckByName.setBackground(Color.BLACK);
    theBtCheckByName.setForeground(Color.BLUE);
    theBtCheckByName.addActionListener(
    		e -> cont.doCheckByName( theInput.getText(),Integer.parseInt(buyQuantity.getText()) ) );
    cp.add(theBtCheckByName);
  			

    theBtBuy.setBounds( 16, 25+50*2, 80, 40 );      // Buy button 
    theBtBuy.addActionListener(                     // Call back code
      e -> cont.doBuy() );
    theBtBuy.setBackground(Color.CYAN);
    theBtBuy.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    theBtBuy.setFont(new Font("Arial", Font.BOLD, 14));
    theBtBuy.setContentAreaFilled(false);
    theBtBuy.setOpaque(true);
    cp.add( theBtBuy );                             //  Add to canvas

    theBtBought.setBounds( 16, 25+50*3, 80, 40 );   // Bought Button
    theBtBought.addActionListener(                  // Call back code
      e -> cont.doBought() );
    cp.add( theBtBought );                          //  Add to canvas

    theAction.setBounds( 110, 25 , 270, 30 );       // Message area
    theAction.setText( "" );                        // Blank
    cp.add( theAction );                            //  Add to canvas
    
    buyQuantity.setBounds(110, 95, 120, 30);		// Adding the textfield to the GUI
    buyQuantity.setText("1");						// Adding text to the JTextField
    cp.add(buyQuantity);							// Adding to canvas

    theInput.setBounds( 110, 60, 200, 30 );         // Input Area
    theInput.setText("");                           // Blank
    cp.add( theInput );                             //  Add to canvas

    theSP.setBounds( 110, 130, 270, 100 );          // Scrolling pane
    theOutput.setText( "" );                        //  Blank
    theOutput.setFont( f );                         //  Uses font  
    cp.add( theSP );                                //  Add to canvas
    theSP.getViewport().add( theOutput );           //  In TextArea
    rootWindow.setVisible( true );                  // Make visible
    theInput.requestFocus();                        // Focus is here
  }

  /**
   * The controller object, used so that an interaction can be passed to the controller
   * @param c   The controller
   */

  public void setController( CashierController c )
  {
    cont = c;
  }

  /**
   * Update the view
   * @param modelC   The observed model
   * @param arg      Specific args 
   */
  @Override
  public void update( Observable modelC, Object arg )
  {
    CashierModel model  = (CashierModel) modelC;
    String      message = (String) arg;
    theAction.setText( message );
    Basket basket = model.getBasket();
    if ( basket == null )
      theOutput.setText( "Your basket is empty." );
    else
      theOutput.setText( basket.getDetails() );
    
    theInput.requestFocus();               // Focus is here
  }

}
