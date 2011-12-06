package com.convolutededge.androidgames.slimysam;

import com.convolutededge.androidgames.framework.Screen;
import com.convolutededge.androidgames.framework.impl.AndroidGame;

public class SlimySamGame extends AndroidGame {
	@Override
	public Screen getStartScreen() {
		return new LoadingScreen(this);
	}
}