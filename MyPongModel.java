package pong;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;
import java.util.Set;


public class MyPongModel implements PongModel{
	private int barPosL;
	private int barPosR;
	private int barHeightL;
	private int barHeightR;
	private Point ballPos;
	private String scoreL;
	private String scoreR;
	private Dimension fieldSize;
	private String message;
	private int Lscore = 0;
	private int Rscore = 0;
	private int barHits = 0;
	private int speed = 3;
	private double ballAngle = Random();
	private int startspeed = this.speed;
	private double outAngle = 0;
	//	private Double ballAngle = 1.0;
	//	private Double movement_vectorX = this.speed*Math.sin(this.ballAngle);
	//	private Double movement_vectorY = this.speed*Math.sin(this.ballAngle);





	public MyPongModel(String LeftPlayer, String RightPlayer) {
		this.barPosL = 150;
		this.barPosR = 150;
		this.barHeightL = 50;
		this.barHeightR = 50;
		this.fieldSize = new Dimension(600,300);
		this.ballPos = new Point(fieldSize.width/2,fieldSize.height/2);
		this.scoreL = "0";
		this.scoreR = "0";
		this.message = "Move The Bar To Start The Game";
	}

	public double getBallOutAngle(Point target, int barPos, int barHeight, int factor){	
		if ((target.getY()) < (barPos- (barHeight/2) + (barHeight/3))){
			this.outAngle = (Math.PI*7/4)*factor + Math.PI;
			System.out.println("Övre");
			return this.outAngle;
		}
		if ((target.getY()) > (barPos + (barHeight/2) - (barHeight/3))){
			this.outAngle = (Math.PI/4)*factor + Math.PI;
			System.out.println("Undre");
			return this.outAngle;
		} 
		else{
			this.outAngle = Math.PI*factor;
			System.out.println("mitten");
			return this.outAngle;
		}
	}


	public void moveBars (Set<Input> input){
		for (Input i : input) {
			this.message = null;
			switch (i.key){
			case LEFT:				
				switch (i.dir){
				case UP:
					if (this.barPosL > (barHeightL/2)){
						this.barPosL = this.barPosL - 5;
					}
					break;
				case DOWN:
					if (this.barPosL < 300-(barHeightL/2)){
						this.barPosL += 5;
					}
					break;
				}
				break;

			case RIGHT:
				switch(i.dir){
				case UP:
					if (this.barPosR > (barHeightR/2)){
						this.barPosR += -5;
					}
					break;
				case DOWN:
					if (this.barPosR < 300- (barHeightR/2)){
						this.barPosR = this.barPosR + 5;
					}
					break;
				}
				break;
			}
		}
	}
	public Double Random(){
		Double M = null;
		Random ran = new Random();

		if(ran.nextInt(6) == 0){
			M = 0.0;			
		}
		else if(ran.nextInt(6) == 1){
			M = Math.PI*3/4;				
		}
		else if(ran.nextInt(6) == 2){
			M = Math.PI*1/4;	
		}
		else if(ran.nextInt(6) == 3){
			M = Math.PI;	
		}
		else if(ran.nextInt(6) == 4){
			M = -Math.PI*3/4;	
		}
		else if(ran.nextInt(6) == 5){
			M = -Math.PI*1/4;	
		}
		else{
			M = 0.0;
		}
		return M;
	}



	public void moveBall(){
		if(this.message == null){
			if(this.barHits >= 5){
				this.speed += 1;
				this.barHits -= 5;
			}
			Double movement_vectorX = this.speed* Math.cos(this.ballAngle);
			Double movement_vectorY = this.speed* Math.sin(this.ballAngle);
			int bartemp = this.barHeightR;
			Double X = this.ballPos.getX();
			Double Y = this.ballPos.getY();
			Point newPos = this.ballPos;
			if (Y >= (this.fieldSize.getHeight() - 12) || Y <= 10){
				this.ballAngle = 2*Math.PI - this.ballAngle; 
				movement_vectorY = this.speed*Math.sin(this.ballAngle);
			}
			if(Y + 10 >= (this.barPosR - this.barHeightR/2) && (Y -10) <= this.barPosR + this.barHeightR/2 && X >= this.fieldSize.getWidth()-12){
				this.ballAngle = getBallOutAngle(this.ballPos, this.barPosR, this.barHeightR, -1);
			/*	if ((this.ballPos.getY() + 10) < (this.barPosR - (this.barHeightR/2) + (this.barHeightR/3))){
					this.ballAngle = (Math.PI*7/4) + Math.PI; }
					else if((this.ballPos.getY() - 10) < (this.barPosR + (this.barHeightR/2) - (this.barHeightR/3))){
					this.ballAngle = (Math.PI/4) + Math.PI;	
					}
					else{
						this.ballAngle = Math.PI;
					}
				*/
				movement_vectorX = this.speed*Math.cos(this.ballAngle);
				movement_vectorY = this.speed*Math.sin(this.ballAngle);
				this.barHits++;


			}
			else if(X >= this.fieldSize.getWidth()){
				X = this.fieldSize.getWidth()/2;
				Y = this.fieldSize.getHeight()/2;
				this.Lscore++;
				if(this.Lscore != 5){
					if(this.Lscore <= this.Rscore){
						this.barHeightL = this.barHeightL - bartemp/10;
					}
					if(this.Rscore < this.Lscore){
						this.barHeightR = this.barHeightR + bartemp/10;
					} 
				}
				this.ballAngle = Random();
				this.speed = this.startspeed;
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			if( Y + 10 >= (this.barPosL - this.barHeightL/2) && Y - 10<= this.barPosL + this.barHeightL/2 && X <= 12){
				this.ballAngle = Math.PI - getBallOutAngle(this.ballPos, this.barPosL, this.barHeightL, -1);
			/*	if ((this.ballPos.getY() + 10) < (this.barPosL - (this.barHeightL/2) + (this.barHeightL/3))){
					this.ballAngle = (Math.PI*7/4) + Math.PI; }
					else if((this.ballPos.getY() - 10) < (this.barPosL + (this.barHeightL/2) - (this.barHeightL/3))){
					this.ballAngle = (Math.PI/4) + Math.PI;	
					}
					else{
						this.ballAngle = Math.PI;
					}
				*/
				movement_vectorX = this.speed*Math.cos(this.ballAngle);
				movement_vectorY = this.speed*Math.sin(this.ballAngle);
				this.barHits++;
			}
			else if(X <= 0){
				X = this.fieldSize.getWidth()/2;
				Y = this.fieldSize.getHeight()/2;
				this.Rscore++;
				if(this.Rscore != 5){
					if(this.Lscore < this.Rscore){
						this.barHeightL = this.barHeightL + bartemp/10;
					}
					if(this.Rscore <= this.Lscore){
						this.barHeightR = this.barHeightR - bartemp/10;
					} 
				}
				this.ballAngle = Random();
				this.speed = this.startspeed;
				try {
					Thread.sleep(1000);
				} catch(InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}
			newPos.setLocation(X+movement_vectorX,Y+movement_vectorY);
			this.ballPos = newPos;
			this.scoreR = Integer.toString(this.Rscore);
			this.scoreL = Integer.toString(this.Lscore);
		}
	}


	public void compute(Set<Input> input, long delta_t){
		if(this.message == "Player1 wins"){
			try {
				Thread.sleep(2000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.exit(0);
		}
		if(this.message == "Player2 wins"){
			try {
				Thread.sleep(2000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.exit(0);
		}		

		moveBars(input);
		moveBall();

		if(this.Lscore == 5){
			this.message = "Player1 wins";			
		}
		else if(this.Rscore == 5){
			this.message = "Player2 wins";
		}
	}


	public int getBarPos(BarKey k){
		switch (k){
		case LEFT:
			return this.barPosL;
		case RIGHT:
			return this.barPosR;
		default: 
			return 0;
		}
	}


	public int getBarHeight(BarKey k) {
		switch (k){
		case LEFT:
			return this.barHeightL;
		case RIGHT:
			return this.barHeightR;
		default: 
			return 0;
		}
	}


	public Point getBallPos() {
		return this.ballPos;
	}

	public String getMessage() {
		return this.message;
	}


	public String getScore(BarKey k) {
		switch (k){
		case LEFT:
			return this.scoreL;
		case RIGHT:
			return this.scoreR;
		default: 
			return "0";
		}
	}

	public Dimension getFieldSize() {
		return this.fieldSize;
	}

}