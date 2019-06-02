package server;

import application.Controller;
import application.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Class for Routing requests to controllers
 */
class Router {

    private Map<String, Controller> underlying = new HashMap<>();
    private ClassLoader classLoader;

    Router(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    /**
     * @param urlPattern eg. /login
     * @param controllerClassName ClassName to load and dispatch request to.
     * @return self
     * @throws InvalidRouteException when failed to load controller class
     */
    Router add(String urlPattern, String controllerClassName) throws InvalidRouteException {
        try {
            Controller controller = (Controller) Class.forName(controllerClassName, true, classLoader).newInstance();
            underlying.put(urlPattern, controller);
        } catch (ReflectiveOperationException e) {
            String message = String.format("Failed to add route. urlPattern=%s, controllerClassName=%s", urlPattern, controllerClassName);
            throw new InvalidRouteException(message, e);
        }
        return this;
    }


    /**
     * Invoke registered controller with given request.
     * If controller is not found, returns NotFound response.
     */
    application.Response invoke(String urlPattern, application.Request request) {
        Controller controller = underlying.get(urlPattern);
        Request.Method method = request.getMethod();

        if (method == Request.Method.Get) {
            return controller.doGet(request);
        } else if (method == Request.Method.Post){
            return controller.doPost(request);
        } else {
            return null;// TODO
        }
    }

    class InvalidRouteException extends RuntimeException {
        InvalidRouteException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
