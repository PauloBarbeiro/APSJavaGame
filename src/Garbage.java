import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Garbage extends JPanel{
	
	private int x, y,height = 10, width = 10;

	static int score = 0;

	private BufferedImage sprite;
	private boolean especial = false;
	
	public Garbage(int x, int y){
		this.x = x;
		this.y = y;
		
		Loader loader = new Loader();
		BufferedImage temp = null;
		
		try{
			temp = loader.loadImage("images/maptiles.png");
		}catch(IOException ex){
			System.out.println("Erro carregando Sprite Avatar");
		}
		
		SpriteSheet ss = new SpriteSheet(temp);
		this.sprite = ss.grabSprite( 32*26, 32*27, 32, 32 );
		temp = null;
		
	}
	
	public Garbage(int x, int y, boolean s){
		this.x = x;
		this.y = y;
		this.especial = s;
		
		Loader loader = new Loader();
		BufferedImage temp = null;
		
		try{
			temp = loader.loadImage("images/maptiles.png");
		}catch(IOException ex){
			System.out.println("Erro carregando Sprite Avatar");
		}
		
		int xlixo = 26;
		if(s){ xlixo = 28; }
		System.out.println(x+" - "+y+" - "+xlixo);
		
		SpriteSheet ss = new SpriteSheet(temp);
		this.sprite = ss.grabSprite( 32*28, 32*27, 32, 32 );
		temp = null;
		
	}
	
	
	public void paint(Graphics g){
		super.paint(g);
		//g.setColor(Color.PINK);
		//g.fillRect(x, y, height, width);	
		g.drawImage(this.sprite, x, y, 32, 32, null);
	}
	
	//fun�ao de colis�o
	public boolean collision(Avatar av,Garbage lixo){
		boolean colission = false;
		if(this.x > av.getX() && this.x < av.getX() + 64){
			if(this.y > av.getY() && this.y < av.getY() + 64){
				colission = true;
				TelaGamePlay.grabGarbage.play();
				this.anular();
				lixo = null;
			}
		}
		return colission;
	}
	
	
	public void anular(){
		this.height = 0;
		this.width = 0;
		this.x = -100;
		this.y= -100;
	}
}