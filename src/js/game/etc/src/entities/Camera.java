package js.game.etc.src.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;
	
	
	private Vector3f position = new Vector3f(100, 35, 50);
	private float pitch = 10;
	private float yaw = 0;
	private float roll;
	
	private Player player;

	public Camera(Player player) {
		this.player = player;
	}

	public void move() {
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		float horizDistance = calculateHorizontalDistance();
		float verticDistance = calculateVerticalDistance();
		calculateCameraPosition(horizDistance, verticDistance);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}
	
	private void calculateCameraPosition(float horizDistance, float verticDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getCamY() + verticDistance;
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		
	}
	
	private float calculateHorizontalDistance(){
		float hDLimit = (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
		if (hDLimit < 0){
			hDLimit = 0;
		}
		return hDLimit;
	}
	private float calculateVerticalDistance(){
		float vDLimit = (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
		if (vDLimit < 0){
			vDLimit = 0;
		}
		return vDLimit;
	}
	
	private void calculateZoom(){
		if(distanceFromPlayer >= 1.5f){
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		distanceFromPlayer -= zoomLevel;
		}else {
			distanceFromPlayer = 2;
		}
		
		
	}
	
	
	
	private void calculatePitch(){
		if(Mouse.isButtonDown(1)){
			float rotationChange = Mouse.getDX() * 0.3f;
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
			player.increaseRotation(0, -rotationChange, 0);
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
		
	}
	
	
	private void calculateAngleAroundPlayer(){
		if(Mouse.isButtonDown(0)){
			float angleChange = Mouse.getDX() * 0.3f;
			angleAroundPlayer -= angleChange;
			float pitchChange = Mouse.getDY() * 0.1f;
			pitch -= pitchChange;
		}
	}

}
