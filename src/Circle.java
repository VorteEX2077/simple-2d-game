import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Circle extends Component {

    private Color color;
    private int circleYCords;
    private int circleXCords;
    private boolean isReachedRightEnd;
    private boolean isReachedLeftEnd;
    private boolean isReachedBottom;
    private boolean isReachedTop;
    private int deltaDirection;
    private int speed;
    private Ellipse2D ellipse2d;
    private int topLeftY = 60;
    private int topLeftX = 50;
    private final Random random;
    private int wallsPadding = 50;
    private int radius;
    private int bottomScreen;

    public Circle(Color color, int radius) {
        this.color = color;
        this.radius = radius;
        this.isReachedLeftEnd = true;
        this.circleXCords = Window.WIDTH / 2;
        this.circleYCords = 75;
        this.speed = 5;
        this.deltaDirection = 5;
        random = new Random();
        ellipse2d = new Ellipse2D.Float();

        boolean isTemp = random.nextBoolean();
        if(isTemp) {
            isReachedTop = true;
            isReachedBottom = false;
            isReachedRightEnd = true;
            isReachedLeftEnd = false;
        } else {
            isReachedTop = false;
            isReachedBottom = true;
            isReachedRightEnd = false;
            isReachedLeftEnd = true;
        }
    }

    public void ballMovement(boolean isFreezeOn) {
        bottomScreen = Window.HEIGHT - 80;
        if(!isFreezeOn) {
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

        /* add collisions by setting frame of the ellipse2D */
        ellipse2d.setFrame(circleXCords, circleYCords, ConstantVariables.CIRCLE_DIMENSIONS,
                ConstantVariables.CIRCLE_DIMENSIONS);
    }

    public void changeSpeedAndDirection() {
        speed = random.nextInt(10, 15);
        deltaDirection = random.nextInt(5, 10);
    }

    public Ellipse2D getEllipse2D() {
        return ellipse2d;
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
