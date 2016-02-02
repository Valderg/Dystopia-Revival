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
		
		
		RawModel model = OBJLoader.loadObjModel("stall", loader);

		TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("stallTexture")));
		ModelTexture texture = staticModel.getTexture();
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		Entity entity = new Entity(staticModel, new Vector3f(0, 0, -25), 0, 0, 0, 1);
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		Camera camera = new Camera();

		Terrain terrain = new Terrain(-1, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(0, -1, loader, new ModelTexture(loader.loadTexture("grass")));
		
		
		List<Entity> allTrees = new ArrayList<Entity>();
		Random random = new Random();

		

		while (!Display.isCloseRequested()) {
			entity.increaseRotation(0f, 0.02f, 0f);
			camera.move();
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			renderer.processEntity(entity);
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
}
	
}
