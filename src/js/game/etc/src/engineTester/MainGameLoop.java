package js.game.etc.src.engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import js.game.etc.src.entities.Camera;
import js.game.etc.src.entities.Entity;
import js.game.etc.src.entities.Light;
import js.game.etc.src.entities.Player;
import js.game.etc.src.models.RawModel;
import js.game.etc.src.models.TexturedModel;
import js.game.etc.src.objConverter.ModelData;
import js.game.etc.src.objConverter.OBJFileLoader;
import js.game.etc.src.renderEngine.DisplayManager;
import js.game.etc.src.renderEngine.Loader;
import js.game.etc.src.renderEngine.MasterRenderer;
import js.game.etc.src.renderEngine.OBJLoader;
import js.game.etc.src.terrains.Terrain;
import js.game.etc.src.textures.ModelTexture;
import js.game.etc.src.textures.TerrainTexture;
import js.game.etc.src.textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();

		// ***************TERRAIN TEXTURE PACK CREATION***************

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// ***********************************************************
		MasterRenderer renderer = new MasterRenderer();

		// TREE
		ModelData data = OBJFileLoader.loadOBJ("tree");

		RawModel treeModel = loader.loadToVAO(data.getVertices(), data.getTextureCoords(), data.getNormals(),
				data.getIndices());

		TexturedModel staticModel = new TexturedModel(treeModel, new ModelTexture(loader.loadTexture("tree")));

		// GRASS
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);

		// FLOWERS
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
				new ModelTexture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);

		// FERN
		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
				new ModelTexture(loader.loadTexture("fern")));
		fern.getTexture().setHasTransparency(true);

		// LOW POLY TREE
		TexturedModel lowPolyTree = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
				new ModelTexture(loader.loadTexture("lowPolyTree")));

		// PLAYER

		TexturedModel person = new TexturedModel(OBJLoader.loadObjModel("person", loader),
				new ModelTexture(loader.loadTexture("playerTexture")));

		Player player = new Player(person, new Vector3f(1200, 0, -50), 0, 180, 0, 0.7f);
		Camera camera = new Camera(player);

		// ModelTexture texture = staticModel.getTexture();
		// texture.setShineDamper(10);
		// texture.setReflectivity(1);

		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> trees = new ArrayList<Entity>();
		List<Terrain> terrainList = new ArrayList<Terrain>();
		Terrain[][] terrains = new Terrain[3][3];

		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain2 = new Terrain(1, -1, loader, texturePack, blendMap, "heightmap");
		Terrain terrain3 = new Terrain(2, -1, loader, texturePack, blendMap, "heightmap");
		terrainList.add(terrain);
		terrainList.add(terrain2);
		terrainList.add(terrain3);
		terrains[0][0] = terrain;
		terrains[1][0] = terrain2;
		terrains[2][0] = terrain3;

		Random random = new Random(676452);

		for (int i = 0; i < 400; i++) {

			if (i % 1 == 0) {
				float x = random.nextFloat() * 2400 - 400;
				float z = random.nextFloat() * -600;
				int gridX = (int) (x / 800);
				int gridZ = (int) (z / 800);
				Terrain currentTerrain = terrains[gridX][gridZ];
				float y = currentTerrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.6f));
			}

			if (i % 5 == 0) {
				float x = random.nextFloat() * 2400;
				float z = random.nextFloat() * -600;
				int gridX = (int) (x / 800);
				int gridZ = (int) (z / 800);
				Terrain currentTerrain = terrains[gridX][gridZ];
				float y = currentTerrain.getHeightOfTerrain(x, z);
				trees.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));

				x = random.nextFloat() * 2400;
				z = random.nextFloat() * -600;
				gridX = (int) (x / 800);
				gridZ = (int) (z / 800);
				currentTerrain = terrains[gridX][gridZ];
				y = currentTerrain.getHeightOfTerrain(x, z);
				trees.add(new Entity(lowPolyTree, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
						random.nextFloat() * 0.1f + 0.6f));

			}
			// if (i % 3 == 0) {
			// float x = random.nextFloat() * 2400;
			// float z = random.nextFloat() * -600;
			// int gridX = (int) (x / 800);
			// int gridZ = (int) (z / 800);
			// Terrain currentTerrain = terrains[gridX][gridZ];
			// float y = currentTerrain.getHeightOfTerrain(x, z);
			// entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0,
			// 1.8f));
			//
			// x = random.nextFloat() * 2400;
			// z = random.nextFloat() * -600;
			// gridX = (int) (x / 800);
			// gridZ = (int) (z / 800);
			// currentTerrain = terrains[gridX][gridZ];
			// y = currentTerrain.getHeightOfTerrain(x, z);
			// entities.add(new Entity(flower, new Vector3f(x, y, z), 0, 0, 0,
			// 2.3f));
			// }
		}
		Light light = new Light(new Vector3f(20000, 40000, 20000), new Vector3f(1, 1, 1));

		while (!Display.isCloseRequested()) {
			int gridX = (int) (player.getPosition().x / 800);
			int gridZ = (int) (player.getPosition().z / 800);
			Terrain currentTerrain = terrains[gridX][gridZ];
			player.move(currentTerrain);
			camera.move();

			renderer.processEntity(player);
			for (Terrain terrainsToRender : terrainList) {
				renderer.processTerrain(terrainsToRender);
			}

			// renderer.processTerrain(terrain);
			// renderer.processTerrain(terrain2);
			// renderer.processEntity(entity);
			for (Entity entity : entities) {
				renderer.processEntity(entity);
			}
			for (Entity tree : trees) {
				renderer.processEntity(tree);
			}
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}

}
