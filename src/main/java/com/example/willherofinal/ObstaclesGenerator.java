package com.example.willherofinal;

public class ObstaclesGenerator {

    public static GameObj generateObstacle(int id, float x, float y) {
        GameObj obj = null;

        if (id == 1) {
            //Island1
            String imgAddr = "src/main/java/com/example/willherofinal/img/island1.png";
            return new Island(id, x, y, imgAddr);

        } else if ( id == 2 ) {
            //Island 2
            String imgAddr = "src/main/java/com/example/willherofinal/img/island2.png";
            return new Island(id, x, y, imgAddr);

        } else if ( id == 3 ) {
            //Island 3
            String imgAddr = "src/main/java/com/example/willherofinal/img/island3.png";
            return new Island(id, x, y, imgAddr);

        } else if ( id == 4 ){
            //Island 4
            String imgAddr = "src/main/java/com/example/willherofinal/img/island4.png";
            return new Island(id, x, y, imgAddr);

        } else if ( id == 5 ) {
            //GreenOrc
            String imgAddr = "src/main/java/com/example/willherofinal/img/Orc2.png";
            return new GreenOrc(id, x, y, imgAddr);

        } else if ( id == 6 ) {
            //RedOrc
            String imgAddr = "src/main/java/com/example/willherofinal/img/RedOrc1.png";
            return new RedOrc(id, x, y, imgAddr);

        } else if ( id == 7 ) {
            //bossOrc
            String imgAddr = "src/main/java/com/example/willherofinal/img/OrcBoss.png";

        }
        return  obj;
    }
}
