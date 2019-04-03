package core.service;

import builder.GroundBoxElement;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// 0.54 per x offset
@Service
public class MapService {
    private List<GroundBoxElement> map = new ArrayList<>();

    public void generateMap() {

        float lastX = 10.801f;
        float lastY = -4.69f;

        for (int i = 0; i < 20; i++) {
            /*
            x = 10.801 y = -4.702
             */

            GroundBoxElement groundBoxElement = new GroundBoxElement();

            groundBoxElement.setId(i);
            groundBoxElement.setVisible(true);
            groundBoxElement.setX(lastX);
            groundBoxElement.setY(lastY);

            map.add(groundBoxElement);

            lastX += 0.54f;
        }
    }

    public List<GroundBoxElement> getMap() {
        return map;
    }
}
