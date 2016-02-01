package js.game.etc.src.engineTester;

import org.lwjgl.opengl.Display;

import js.game.etc.src.models.RawModel;
import js.game.etc.src.models.TexturedModel;
import js.game.etc.src.renderEngine.DisplayManager;
import js.game.etc.src.renderEngine.Loader;
import js.game.etc.src.renderEngine.Renderer;
import js.game.etc.src.shaders.StaticShader;
import js.game.etc.src.textures.ModelTexture;

public class MainGameLoop {

	
	public static void main(String[] args){
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
				-0.5f, 0.5f, 0f,
				-0.5f, -0.5f, 0f,
				0.5f, -0.5f, 0,
				0.5f, 0.5f, 0
		};
		
		int[] indices = {
				0, 1, 3,	//Top left triangle
				3, 1, 2,	//Bottom right triangle
		};
		
		float[] textureCoords = {
			0,0,
			0,1,
			1,1,
			1,0
				
				
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("punisher"));
		TexturedModel texturedModel = new TexturedModel(model,texture);
		
		while(!Display.isCloseRequested()){
			renderer.prepare();
			shader.start();
			renderer.render(texturedModel);
			shader.stop();
			DisplayManager.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
		
	}
}
