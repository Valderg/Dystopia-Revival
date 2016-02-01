package js.game.etc.src.engineTester;

import org.lwjgl.opengl.Display;

import js.game.etc.src.renderEngine.DisplayManager;
import js.game.etc.src.renderEngine.Loader;
import js.game.etc.src.renderEngine.RawModel;
import js.game.etc.src.renderEngine.Renderer;

public class MainGameLoop {

	
	public static void main(String[] args){
		
		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
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
		
		RawModel model = loader.loadToVAO(vertices, indices);
		
		while(!Display.isCloseRequested()){
			renderer.prepare();
			renderer.render(model);
			
			DisplayManager.updateDisplay();
		}
		
		DisplayManager.closeDisplay();
		
	}
}
