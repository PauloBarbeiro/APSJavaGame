import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/*
 * Parque Mapa
 * 
 * Organiza o mapa usado como plano de fundo para o jogo.
 * 
 * O cenário de fundo é dividido em quadrantes, aqui chamados de "cells".
 * Cada cell é uma instancia de MapCell.
 * 
 * Esta classe recebe a largura e latura desejada para o mapa, uma medida para os quadrantes, 
 * e um array que diz qual deve ser o trecho do tile a ser usado. 
 * 
 * Aqui também está a função de renderização do mapa, e suas celulas.
 * 
 * */

public class ParqueMapa extends JPanel{
	
	private int width, height;
	private int cellSize;
	private int[][] map;
	private ArrayList<MapCell> cells;
	
	ParqueMapa(int w, int h, int cellSize, int[][] mapa){
		
		this.width = w;
		this.height = h;
		this.cellSize = cellSize;
		this.map = mapa;
		
		this.cells = new ArrayList<MapCell>();
		
		this.setupMap();
		
	}
	
	private void setupMap(){
		int qtd_width = this.width / this.cellSize;
		int qtd_height = this.height / this.cellSize;
		//int totalCells = this.map.length;
		
		int x = 0;
		int y = 0;
		
		for(y=0 ; y<qtd_height ; y++){
			for(x=0 ; x<qtd_width ; x++){
				this.cells.add( new MapCell( x*this.cellSize, y*this.cellSize, this.cellSize, this.map[y][x]) );
			}
		}
		
	}
	
	public void paint(Graphics g){
		//desenhanda mapa
		super.paint(g);
		//g.drawImage(this.animator.sprite, x, y, 64, 64, null);
		for(int i=0 ; i<this.cells.size() ; i++){
			MapCell c = this.cells.get( i );
			c.paint(g);
		}
	}
	
}
