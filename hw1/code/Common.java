import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Common {
    private static final String title = "Gold Wars";
    private static final int windowWidth = 1650;
    private static final int windowHeight = 1000;

    private static final GoldPrice goldPrice = new GoldPrice(588, 62);

    private static final Random randomGenerator = new Random(1234);
    private static final int upperLineY = 100;

    //I initialized this, not sure allowed
    private static Country china;
    private static Country usa;
    private static Country israel;
    private static Country turkey;
    private static Country russia;

    private static Agent mit;
    private static Agent mossad;
    private static Agent svr;
    private static Agent cia;
    private static Agent mss;

    private static final ArrayList<Order> listOfOrders;


    static  {
        //  initialize countries
        china = new Country(1300, 700, "china", "CH", 10000, 50);
        usa = new Country(100, 700, "usa", "US", 10000, 50);
        israel = new Country(400, 700, "israel", "IL", 10000, 50);
        turkey = new Country(700,700,"turkey", "TR", 10000, 50);
        russia = new Country(1000, 700, "russia", "RU",10000, 50);

        //  initialize IAs
        mit = new Expert(700,300, new Master(700, 300, new Novice(700, 300,
                new BasicAgent(700 ,300, 0, getTurkey(), "mit"))));
        mossad = new Expert(400,300, new Master(400, 300, new Novice(400, 300,
                new BasicAgent(400 ,300, 0, getIsrael(), "mossad" ))));
        svr = new Expert(1000,300, new Master(1000, 300, new Novice(1000, 300,
                new BasicAgent(1000 ,300, 0, getRussia(), "svr"))));
        cia = new Expert(100,300, new Master(100, 300, new Novice(100, 300,
                new BasicAgent(100 ,300, 0, getUSA(), "cia"))));
        mss = new Expert(1300,300, new Master(1300, 300, new Novice(1300, 300,
                new BasicAgent(1300 ,300, 0, getChina(), "mss"))));

        // initialize order list
        listOfOrders = new ArrayList<Order>();
    }

    // getters
    public static String getTitle() { return title; }
    public static int getWindowWidth() { return windowWidth; }
    public static int getWindowHeight() { return windowHeight; }

    // getter
    public static GoldPrice getGoldPrice() { return goldPrice; }

    // getters for IAs
    public static Country getChina() { return china; }
    public static Country getUSA() { return usa; }
    public static Country getIsrael() { return israel; }
    public static Country getTurkey() { return turkey; }
    public static Country getRussia() { return russia; }
    public static Agent getMIT() { return mit; }
    public static Agent getCIA() { return cia; }
    public static Agent getMOSSAD() { return mossad; }
    public static Agent getSVR() { return svr; }
    public static Agent getMSS() { return mss; }

    public static void addOrder(Order order){
        listOfOrders.add(order);
    }

    public static ArrayList<Order> getListOfOrders(){
        return listOfOrders;
    }

    // getters
    public static Random getRandomGenerator() { return randomGenerator; }
    public static int getUpperLineY() { return upperLineY; }

    public static void stepAllEntities() {
        if (randomGenerator.nextInt(200) == 0) goldPrice.step();

        // step countries
        turkey.step();
        china.step();
        usa.step();
        russia.step();
        israel.step();

        // step IAs
        mss.step();
        mit.step();
        cia.step();
        svr.step();
        mossad.step();

        // step orders if they do not exceed window limitations
        // if they exceed, remove their iterators
        Iterator itr = getListOfOrders().iterator();
        while (itr.hasNext())
        {
            Order o = (Order)itr.next();
            Position p = o.getPosition();
            int x = p.getIntX();
            int y = p.getIntY();
            if(x <= 1650 && x >= 0 && y == 100) {
                itr.remove();
            }
            else if(x >= 1650 || x <= 0 || y <= 100){
                itr.remove();
            }
            else{
                o.step();
            }
        }

    }
}