import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Array;
import javax.swing.*;

/*	TelaGamePlay
 * 
 * Tarefas:
 *  - coordenar elementos de jogo, regras e pontuação.
 *  - Captura de eventos
 *  - Atualização dos objetos
 *  - Renderização
 *   
 * 
 * É uma subclasse de Gameloop. Logo a atualização dos quadros é feita de maneira organizada.
 * Durante sua construção, os objetos de jogo são instanciados.
 *  
 * Essa classe instancia os objetos de jogos, e outros acessórios importantes.
 * Objetos de jogo:
 * 	- Avatar
 *  - ParqueMapa
 *  _ Garbage
 *  
 *  Acessórios:
 *  - Loader
 *  - Sound
 * 
 * 
 * 
 * */
public class TelaGamePlay extends GameLoop implements ActionListener, KeyListener{
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 576;
	
	public static boolean telaConcluida = false;

	
	private Game game;
	public Loader loader;
	Avatar av;
	
	Sound soundTrack;
	public static Sound grabGarbage;
	
	
	
	//Array de lixos
	Garbage[] lixo = new Garbage[10];
	
	//lixo especial
	Garbage lixoEspecial;
	
	//painel do Score
	ScorePanel sp;
	
	//Mapa do Parque
	ParqueMapa mapa;
	
	public TelaGamePlay(Game game){
		this.game = game;
		this.loader = new Loader();
	
		//Posicionamento e design do panel da Gameplay
		this.setBounds(0,0, 800,576);

		this.setBackground(Color.BLACK);
		
		addKeyListener(this);
		
		//Ajustando o foco do boneco
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		
		//Avatar
		this.av = new Avatar(this);
		this.add(this.av);
		
		//Mapa do jogo
		int[][] m = {
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 18, 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 5 , 4 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 , 10 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 28, 21, 21, 21, 21 , 21 , 21 , 21 , 21 , 21 , 21 , 21 , 21 , 21 , 21 , 21 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 3 , 10, 10, 10, 10, 10, 10, 10, 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 16, 1 , 1 , 1 , 1 , 1 , 1 , 2 , 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 },
				{0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 3 , 27, 20, 20, 20, 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 , 20 }
		};
		

		this.mapa = new ParqueMapa(TelaGamePlay.WIDTH, TelaGamePlay.HEIGHT, 32, m );

		//Cria��o dos lixos
		lixo[0] = new Garbage(220,5);
		lixo[1] = new Garbage(89,68);
		lixo[2] = new Garbage(125,100);
		lixo[3] = new Garbage(300,10);
		lixo[4] = new Garbage(150,80);
		lixo[5] = new Garbage(20,110);
		lixo[6] = new Garbage(40,200);
		lixo[7] = new Garbage(350,200);
		lixo[8] = new Garbage(200,70);
		lixo[9] = new Garbage(70,300);
		
		//adicionando lixos na Tela
		for(int i=0;i<10;i++){
			this.add(lixo[i]);
		}
		
		//adicionando lixo especial na tela(posi��o fixa)
		this.lixoEspecial = new Garbage(300, 300, true);
		this.add(this.lixoEspecial);
		
		//adicionando painel de score
		this.sp = new ScorePanel();
		this.add(this.sp);

		this.add(this.mapa);
		
		this.grabGarbage = new Sound("/audio/getlixo.wav");
		
		
	}
	
	
	
	//desenha os objetos de jogo
	public void paint(Graphics g){
		super.paint(g);
		
		//Mapa
		this.mapa.paint(g);
		
		//Avatar
		this.av.paint(g);
	
		//painel de Score
		this.sp.paint(g);
		this.sp.ScoreText(g);

		//lixo
		for(int i=0;i<10;i++){
			this.lixo[i].paint(g);;
		}
		
		//A partir de 15 segundos o lixo especial ir� aparecer
		if(TimerGameplay.tempo <= 15){
    		this.lixoEspecial.paint(g);
    	}
	}
	
	//configura inicio do jogo
	public void startup(){
		this.soundTrack = new Sound("/audio/track3.wav");
		this.soundTrack.loop();
	}
	
	//encerra o jogo 
    public void shutdown(){}
    
    //atualiza os objetos de jogo
    public  void update(double delta){
    	this.av.update();
    	
    	//checar� a cada update se haver� colis�o
    	for(int i=0;i<10;i++){
    		//se houver colis�o entre Avatar e os lixos normais, ser�o acrescidos 10 pontos ao player
			if(this.lixo[i].collision(av, lixo[i])){
				ScorePanel.score += 10;
			}
		}
    	
    	//Checa se todos os lixos Foram coletados
    	if(ScorePanel.getScore() == 100 && telaConcluida == false){
    		telaConcluida = true;
    		//JOptionPane.showMessageDialog(null, "Fase 1 conclu�da!!!");
    		//ScorePanel.setScoreToZero();
    	}
    	
    	//se houver colis�o entre Avatar e o lixo especial, ser�o acrescidos 10 segundos a mais para o player
    	if(lixoEspecial.collision(av, lixoEspecial)){
    		TimerGameplay.tempo += 10; 
    	}
    }
    
    //atualiza renderização
    public void draw(){
    	this.repaint();
    }
	
    /*
     * CAPTURA DE EVENTOS
     * */
	@Override
	public void actionPerformed(ActionEvent arg0) {	}//*/

	@Override
	public void keyPressed(KeyEvent e) {
		
		//Avalia se a partida ainda está rodando:
		// Caso postivo: envia o evento para o Avatar
		// Caso negativo: bloqueia o jogo com alertas.
		if(TimerGameplay.tempo >= 1 && TelaGamePlay.telaConcluida == false){
			this.av.keyPressed(e);	
		} else if(TimerGameplay.tempo == 0){
			JOptionPane.showMessageDialog(null, "Seu Tempo Acabou!!!\nFinal Score: " + ScorePanel.getScore(), "Fim de Jogo", JOptionPane.INFORMATION_MESSAGE);
    		JOptionPane.showMessageDialog(null, "Fechando o Jogo...");
		} else if(TelaGamePlay.telaConcluida == true){
			this.av.noMotion();
			JOptionPane.showMessageDialog(null, "Fase 1 conclu�da!!!\nFinal Score: " + ScorePanel.getScore(), "Parab�ns", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.av.keyReleased(e);
	}

	@Override
	public void keyTyped(KeyEvent e) {}	
}
