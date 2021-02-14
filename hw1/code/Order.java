public abstract class Order extends Entity {
    protected Country country;
    protected int amount = Common.getRandomGenerator().nextInt(5) + 1;
    protected int verticalSpeed = Common.getRandomGenerator().nextInt(3)-3;
    protected int horizontalSpeed = Common.getRandomGenerator().nextInt(4) - 2;
    //sell -> 0
    //buy -> 1
    protected int type;

    public Order(double x, double y, Country country, int type) {
        super(x, y);
        this.country = country;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    private int getVerticalSpeed() {
        return verticalSpeed;
    }

    // used by IA while stealing order
    public int getAmount() {
        return amount;
    }

    private int getHorizontalSpeed() {
        return horizontalSpeed;
    }

    // called by IA
    public Country getCountry() {
        return country;
    }

    // move the order
    // not specific to type of order
    public void move(){
        Position p = getPosition();
        int x = p.getIntX();
        int y = p.getIntY();
        p.setX(x + getHorizontalSpeed());
        p.setY(y + getVerticalSpeed());

    }
}