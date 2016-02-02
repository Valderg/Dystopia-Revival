package js.game.etc.src.engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import js.game.etc.src.entities.Camera;
import js.game.etc.src.entities.Entity;
import js.game.etc.src.entities.Light;
import js.game.etc.src.models.RawModel;
import js.game.etc.src.models.TexturedModel;
import js.game.etc.src.renderEngine.DisplayManager;
import js.game.etc.src.renderEngine.Loader;
import js.game.etc.src.renderEngine.MasterRenderer;
import js.game.etc.src.renderEngine.OBJLoader;
import js.game.etc.src.terrains.Terrain;
import js.game.etc.src.textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		MasterRenderer renderer = new MasterRenderer();

		RawModel model = OBJLoader.loadObjModel("tree", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
				new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);
		// ModelTexture texture = staticModel.getTexture();
		// texture.setShineDamper(10);
		// texture.setReflectivity(1);

		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i < 500; i++) {
			entities.add(new Entity(staticModel,
					new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0, 0, 0, 3));
			entities.add(new Entity(grass, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600),
					0, 0, 0, 1));
			entities.add(new Entity(fern, new Vector3f(random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600), 0,
					0, 0, 0.6f));
		}
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		Camera camera = new Camera();

		Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));

		while (!Display.isCloseRequested()) {
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			// renderer.processEntity(entity);
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
