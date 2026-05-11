import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class Beast extends AdvancedRobot {

    double moveDirection = 1;

    public void run() {

        setBodyColor(Color.BLACK);

        while (true) {
            turnRadarRight(360);
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {

        // Só começa a orbitar quando restam 2 robôs
        if (getOthers() == 1) {

            // Ângulo absoluto do inimigo
            double absoluteBearing =
                    getHeadingRadians() + e.getBearingRadians();

            // Movimento perpendicular (90 graus)
            double turn =
                    Utils.normalRelativeAngle(
                            absoluteBearing + Math.PI / 2
                            - getHeadingRadians());

            setTurnRightRadians(turn);

            // Mantém movimento contínuo
            setAhead(130 * moveDirection);

            // Se estiver muito perto, troca direção
            if (e.getDistance() < 60) {
                moveDirection *= -1;
            }

            // Mira simples
            setTurnGunRightRadians(
                    Utils.normalRelativeAngle(
                            absoluteBearing - getGunHeadingRadians()));

            fire(2);
        }
        else {

            // Movimento normal quando há vários inimigos
            setAhead(100);
            setTurnRight(30);
        }

        execute();
    }

    public void onHitWall(HitWallEvent e) {
        moveDirection *= -1;
    }
}