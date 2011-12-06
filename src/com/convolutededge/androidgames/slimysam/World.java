package com.convolutededge.androidgames.slimysam;

import java.util.Random;

public class World {
	static final int WORLD_WIDTH = 10;
	static final int WORLD_HEIGHT = 13;
	static final int SCORE_INCREMENT = 10;
	static final float TICK_INITIAL = 0.5f;
	static final float TICK_DECREMENT = 0.05f;
	
	public Snake snake;
	public Kitten kitten;
	public boolean gameOver = false;
	public int score = 0;
	
	boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
	Random random = new Random();
	float tickTime = 0;
	static float tick = TICK_INITIAL;
	
	
	public World() {
		snake = new Snake();
		placeKitten();
	}
	
	private void placeKitten() {
		for (int x = 0; x < WORLD_WIDTH; x++) {
			for (int y = 0; y < WORLD_HEIGHT; y++) {
				fields[x][y] = false;
			}
		}
		
		int len = snake.parts.size();
		for (int i = 0; i < len; i++) {
			SnakePart part = snake.parts.get(i);
			fields[part.x][part.y] = true;
		}
		
		int kittenX = random.nextInt(WORLD_WIDTH);
		int kittenY = random.nextInt(WORLD_HEIGHT);
		while (true) {
			if (fields[kittenX][kittenY] == false)
				break;
			kittenX += 1;
			if (kittenX >= WORLD_WIDTH) {
				kittenX = 0;
				kittenY += 1;
				if (kittenY >= WORLD_HEIGHT) {
					kittenY = 0;
				}
			}
		}
		kitten = new Kitten(kittenX, kittenY, random.nextInt(3));
	}
	
	public void update(float deltaTime) {
		if (gameOver)
			return;
		
		tickTime += deltaTime;
		
		while (tickTime > tick) {
			tickTime -= tick;
			snake.advance();
			if (snake.checkBitten()){
				gameOver = true;
				return;
			}
			SnakePart head = snake.parts.get(0);
			if (head.x == kitten.x && head.y == kitten.y) {
				score += SCORE_INCREMENT;
				snake.eat();
				if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
					gameOver = true;
					return;
				} else {
					placeKitten();
				}
				
				if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
					tick -= TICK_DECREMENT;
				}
			}
		}
	}
}
