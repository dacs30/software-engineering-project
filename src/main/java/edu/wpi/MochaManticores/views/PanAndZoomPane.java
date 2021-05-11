package edu.wpi.MochaManticores.views;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PanAndZoomPane extends Pane {

    public static final double DEFAULT_DELTA = 1.3d;
    DoubleProperty myScale = new SimpleDoubleProperty(1.0);
    public DoubleProperty deltaY = new SimpleDoubleProperty(0.0);
    private final Timeline timeline;


    public PanAndZoomPane() {

        this.timeline = new Timeline(60);

        // add scale transform
        scaleXProperty().bind(myScale);
        scaleYProperty().bind(myScale);
    }


    public double getScale() {
        return myScale.get();
    }

    public void setScale( double scale) {
        myScale.set(scale);
    }

    public void setPivot( double x, double y, double scale) {
        //System.out.printf("X: %f, Y: %f, SCALE:%f\n",x,y,scale);
        // note: pivot value must be untransformed, i. e. without scaling
        // timeline that scales and moves the node
        if (scale < 1.0d){
            return;
        }
        timeline.getKeyFrames().clear();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(200), new KeyValue(translateXProperty(), getTranslateX() - x)),
                new KeyFrame(Duration.millis(200), new KeyValue(translateYProperty(), getTranslateY() - y)),
                new KeyFrame(Duration.millis(200), new KeyValue(myScale, scale))
        );
        timeline.play();

    }

    public void fitWidth () {
        double scale = getParent().getLayoutBounds().getMaxX()/getLayoutBounds().getMaxX();
        double oldScale = getScale();

        double f = scale - oldScale;

        double dx = getTranslateX() - getBoundsInParent().getMinX() - getBoundsInParent().getWidth()/2;
        double dy = getTranslateY() - getBoundsInParent().getMinY() - getBoundsInParent().getHeight()/2;

        double newX = f*dx + getBoundsInParent().getMinX();
        double newY = f*dy + getBoundsInParent().getMinY();

        setPivot(newX, newY, scale);

    }

    public void resetZoom () {
        double scale = 1.0d;

        double x = getTranslateX();
        double y = getTranslateY();

        setPivot(x, y, scale);
    }

    public void centerOnPoint(double CenterX, double CenterY){




        //System.out.println(xTranslate + " || " + yTranslate);
        //setScale(3);

        translateXProperty().set(this.getWidth()/2 - CenterX);
        translateYProperty().set(this.getHeight()/2 - CenterY);

        //setPivot(CenterX, CenterY,3);

        //setPivot(0,0,3);




//        double delta = PanAndZoomPane.DEFAULT_DELTA;
//
//        double scale = getScale(); // currently we only use Y, same value is used for X
//        double oldScale = scale;
//
//        setDeltaY(100);
//        if (deltaY.get() < 0) {
//            scale /= delta;
//        } else {
//            scale *= delta;
//        }
//
//        double f = (scale / oldScale)-1;
//        double dx = (0 - (getBoundsInParent().getWidth()/2 + getBoundsInParent().getMinX()));
//        double dy = (0 - (getBoundsInParent().getHeight()/2 + getBoundsInParent().getMinY()));
//
//
//        setPivot(dx*f, dy*f, scale);

        //event.consume();






















//        if(CenterX < this.getWidth()/2){
//            CenterX = (this.getWidth()/2) - CenterX;
//            CenterX *=-1;
//        }else{
//            CenterX -= this.getWidth()/2;
//        }
//        if(CenterY < this.getHeight()/2){
//            CenterY = (this.getHeight()/2) - CenterY;
//            CenterY *=-1;
//        }else{
//            CenterY -= this.getHeight()/2;
//        }
//
//        setPivot(CenterX,CenterY,3.0d);
    }

    public double getDeltaY() {
        return deltaY.get();
    }
    public void setDeltaY( double dY) {
        deltaY.set(dY);
    }
}