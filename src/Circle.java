import java.awt.*;
import java.util.Random;

public class Circle extends Component {

    private Color color;
    private int circleYCords;
    private int circleXCords;
    private boolean isReachedRightEnd;
    private boolean isReachedLeftEnd;
    private boolean isReachedBottom;
    private boolean isReachedTop = true;
    private int deltaDirection;
    private int speed;
    private int topLeftY = 60;
    private int topLeftX = 50;
    private final Random random;
    private int wallsPadding = 50;
    private int radius;
    private int bottomScreen = Window.HEIGHT - 80;

    public Circle(Color color, int radius) {
        this.color = color;
        this.radius = radius;
        this.isReachedLeftEnd = true;
        this.circleXCords = Window.WIDTH / 2;
        this.circleYCords = 75;
        this.speed = 5;
        this.deltaDirection = 5;
        random = new Random();
    }

    public void ballMovement() {
        /*if(!isFreezeOn) {*/
        if (isReachedBottom) {
            circleYCords = circleYCords - speed;// Going UP
            if (circleYCords <= topLeftY) {
                isReachedBottom = false;
                isReachedTop = true;
                changeSpeedAndDirection(); // method call
            }
            if (isReachedRightEnd) {
                circleXCords = circleXCords - deltaDirection;
            }
            if (isReachedLeftEnd) {
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if (isReachedTop) {
            circleYCords = circleYCords + speed;   // GOING Down
            if (circleYCords >= bottomScreen - radius - 5) {
                isReachedBottom = true;
                isReachedTop = false;
                changeSpeedAndDirection();
            }
            if (isReachedRightEnd) {
                circleXCords = circleXCords - deltaDirection;
            }
            if (isReachedLeftEnd) {
                circleXCords = circleXCords + deltaDirection;
            }
        }
        if (circleXCords >= Window.WIDTH - wallsPadding - radius) {
            isReachedRightEnd = true;
            isReachedLeftEnd = false;
            changeSpeedAndDirection();
        }
        if (isReachedRightEnd) {
            circleXCords = circleXCords - deltaDirection;
        }
        if (circleXCords <= topLeftX) {
            isReachedLeftEnd = true;
            isReachedRightEnd = false;
            changeSpeedAndDirection();
        }
        if (isReachedLeftEnd) {
            circleXCords = circleXCords + deltaDirection;
        }
    }

    public void changeSpeedAndDirection() {
        speed = random.nextInt(10, 15);
        deltaDirection = random.nextInt(5, 10);
    }

    public int getX() {
        return circleXCords;
    }

    public int getY() {
        return circleYCords;
    }

    public Color getColor() {
        return color;
    }
}
