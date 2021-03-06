package edu.wpi.MochaManticores.views;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Mouse drag context used for scene and nodes.
 */
class DragContext {

    double mouseAnchorX;
    double mouseAnchorY;

    double translateAnchorX;
    double translateAnchorY;

}

/**
 * Listeners for making the scene's canvas draggable and zoomable
 */
public class SceneGestures {

    private final DragContext sceneDragContext = new DragContext();

    PanAndZoomPane panAndZoomPane;

    public SceneGestures( PanAndZoomPane canvas) {
        this.panAndZoomPane = canvas;
    }

    public EventHandler<MouseEvent> getOnMouseClickedEventHandler() {
        return onMouseClickedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMousePressedEventHandler() {
        return onMousePressedEventHandler;
    }

    public EventHandler<MouseEvent> getOnMouseDraggedEventHandler() {
        return onMouseDraggedEventHandler;
    }

    public EventHandler<ScrollEvent> getOnScrollEventHandler() {
        return onScrollEventHandler;
    }

    private final EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        public void handle(MouseEvent event) {

            sceneDragContext.mouseAnchorX = event.getX();
            sceneDragContext.mouseAnchorY = event.getY();

            sceneDragContext.translateAnchorX = panAndZoomPane.getTranslateX();
            sceneDragContext.translateAnchorY = panAndZoomPane.getTranslateY();

        }

    };

    private final EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {
        public void handle(MouseEvent event) {
            double xTranslate = sceneDragContext.translateAnchorX + event.getX() - sceneDragContext.mouseAnchorX;
            double yTranslate = sceneDragContext.translateAnchorY + event.getY() - sceneDragContext.mouseAnchorY;

            //System.out.println(xTranslate + " || " + yTranslate);

            panAndZoomPane.setTranslateX(xTranslate);
            panAndZoomPane.setTranslateY(yTranslate);

            event.consume();
        }
    };

    /**
     * Mouse wheel handler: zoom to pivot point
     */
    private final EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            double delta = PanAndZoomPane.DEFAULT_DELTA;

            double scale = panAndZoomPane.getScale(); // currently we only use Y, same value is used for X
            double oldScale = scale;

            panAndZoomPane.setDeltaY(event.getDeltaY());
            if (panAndZoomPane.deltaY.get() < 0) {
                scale /= delta;
            } else {
                scale *= delta;
            }

            double f = (scale / oldScale)-1;
            double dx = (event.getX() - (panAndZoomPane.getBoundsInParent().getWidth()/2 + panAndZoomPane.getBoundsInParent().getMinX()));
            double dy = (event.getY() - (panAndZoomPane.getBoundsInParent().getHeight()/2 + panAndZoomPane.getBoundsInParent().getMinY()));

            panAndZoomPane.setPivot(f*dx, f*dy, scale);

            event.consume();

        }
    };

    /**
     * Mouse click handler
     */
    private final EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (event.getClickCount() == 2) {
                    panAndZoomPane.resetZoom();
                }
            }
            if (event.getButton().equals(MouseButton.SECONDARY)) {
                if (event.getClickCount() == 2) {
                    panAndZoomPane.fitWidth();
                }
            }
        }
    };
}