package GameLogic;

import Input.KeyListener;
import Input.MouseMove_Listener;
import Input.Mouse_Listener;
import Physics.Physics;
import Physics.PhysicsObject;
import Physics.PhysicsRectangle;
import Sound.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Random;

public class Game {

    public static int restrictionsize_low = 200;
    public static int restrictionsize_high = 600;
    public static int speed = 5;
    public static int radius = 5;
    public static int population = 30;
    private int glistid;
    private PhysicsRectangle playerrectangle;
    private PhysicsRectangle bossrectangle;
    private final String resourcefolder = System.getProperty("user.dir") + "\\res\\";
    private final String [] imagepaths = {"dude.png","0.png","1.png","2.png","3.png","4.png","5.png","6.png","7.png","8.png","9.png","10.png","11.png","12.png","13.png","14.png","15.png","16.png","17.png","18.png","19.png","20.png","21.png","22.png","23.png","24.png","benfront.png","benfrontaggro.png","benfrontfire.png","benleft.png","benleftfire.png","benright.png","benrightfire.png","heart.png","healthbar.png","healthbarfull.png", "gameover.png","fireball.png","leftarm.png","rightarm.png","gamepaused.png","cursor.png","sword.png","mapicon.png","map.png","dudehandsup.png","mapdialog.png","controls.png"}; //47
    private BufferedImage [] bufferedImages;
    private Graphics g;
    private Physics p;
    private JFrame frame;
    private KeyListener kl;
    private boolean attacksprite = false;
    private int attackspirtecooldown = 0;
    public static final int widthoffset = 9, heightoffset = 31; //14, 40
    private int health = 3;
    private double bosshealthpercentage = 0.9;      // 0.1 = 0% 0.9 = 100%
    private BufferedImage bosshealthbarfull = null;
    private boolean bosshealthchanged = true;
    private int gameboardsize = 832;
    private boolean running = true;
    private boolean rerun = false;
    private final boolean debug = false;
    private final boolean showhitboxex = false;
    private int playerdamagecooldown = 0;
    private int bosspattern = 0;
    private int bossattack = 0;
    private PhysicsRectangle border;
    private PhysicsRectangle arm;
    private boolean didkeyspawn = false;
    private boolean gamePaused = false;
    private int pausedcooldown = 0;
    private boolean playerattack = false;
    private int playerattackcooldown = 0;
    private Mouse_Listener mouse_listener;
    private MouseMove_Listener mouseMove_listener;
    private PhysicsRectangle sword = null;
    private int bossdamagecooldown = 0;
    private boolean bossalive = true;
    private PhysicsRectangle map;
    private Point2D addVec;
    private boolean showmap = false;
    private int animation = 0;
    private SoundManager soundManager;

    public Game(){
        init();
        setupFrame();
        setup();
        showcontrols();
        loop();
    }

    public static void main(String [] args)
    {
        new Game();
    }

    private void reset(){
        g.getObjectlist().get(glistid).clear();
        p.getObjectList().clear();
        attacksprite = false;
        attackspirtecooldown = 0;
        health = 3;
        bosshealthpercentage = 0.9;
        bosshealthbarfull = null;
        bosshealthchanged = true;
        running = true;
        rerun = false;
        gamePaused = false;
        bossattack = 0;
        bosspattern = 0;
        playerattackcooldown = 0;
        playerattack = false;
        pausedcooldown = 0;
        bossalive = true;
        bossdamagecooldown = 0;
        sword = null;
        map = null;
        animation = 0;
        showmap = false;
        addVec = null;
        soundManager.unlockMusic();
    }

    private void init(){
        bufferedImages = new BufferedImage[imagepaths.length];
        BufferedImage bf = null;
        BufferedImage [] [] background = new BufferedImage[13][13];
        for(int i = 0; i < imagepaths.length; i++){
            String path = imagepaths[i];
            try {
                URL toload = this.getClass().getResource("/" + path);
                if(toload != null){
                    bf = ImageIO.read(toload);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferedImages[i] = bf;
        }
        mouse_listener = new Mouse_Listener();
        mouseMove_listener = new MouseMove_Listener();
        setupBackground(background);
        g = new Graphics();
        g.setBackground(background);
        bosshealthbarfull = bufferedImages[35];
        p = new Physics();
        glistid = g.addList();
        kl = new KeyListener();
        soundManager = new SoundManager();
    }

    private void setupFrame(){
        frame = new JFrame("Happy Birthday, Timon!");
        frame.setSize(gameboardsize+widthoffset, gameboardsize+heightoffset);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(g.getPanel());
        frame.setIconImage(bufferedImages[0]);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-416,dim.height/2-416);
        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                kl.lostFocus();
            }
        });
        frame.addKeyListener(kl);
        frame.setResizable(false);
        BufferedImage curorImg = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
        frame.addMouseListener(mouse_listener);
        frame.addMouseMotionListener(mouseMove_listener);
        frame.getContentPane().setCursor(Toolkit.getDefaultToolkit().createCustomCursor(curorImg, new Point(0,0),"Blank Cursor"));
        frame.setVisible(true);
    }

    private void setup(){
        border = new PhysicsRectangle(new Point2D.Double(-1000, -1000), null, 1, frame.getHeight()+1050, frame.getWidth()+1050);
        border.setUnmoveable(true);
        border.setIgnoreinsidecollision(true);
        p.addObject(border);
        Random random = new Random();
        playerrectangle = new PhysicsRectangle(new Point2D.Double(380,520), null, 10, 80, 50, 0);
        p.addObject(playerrectangle);
        bossrectangle = new PhysicsRectangle(new Point2D.Double(335,0), null, 10, 270, 160, 1);
        p.addObject(bossrectangle);
        frame.repaint();
    }

    private void showcontrols(){
        for(int i = 0; i < 240; i++){
            g.addObject(glistid,new Graphics.GraphicImage(bufferedImages[47], new Point2D.Double(0,0)));
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //0 1 2
    //3 4 5
    private int getPlayerPos(){
        if(playerrectangle.getPosition().getX() < gameboardsize/3){
            if(playerrectangle.getPosition().getY() < gameboardsize/2){
                return 0;
            }else{return 3;}
        }
        else if(playerrectangle.getPosition().getX() < 2*(gameboardsize/3)){
            if(playerrectangle.getPosition().getY() < gameboardsize/2){
                return 1;
            }else{return 4;}
        }
        else{
            if(playerrectangle.getPosition().getY() < gameboardsize/2){
                return 2;
            }else{return 5;}
        }

    }

    private void bosski(){
        final int armvelocity = 7;
        if(bosshealthpercentage > 0 && bossalive){
            if(debug){return;}
            Random random = new Random();
            int r = random.nextInt(6000);   //60 = 1% /tick -> 1 = 1% /sec
            if(bosspattern > 0){
                if(bosspattern % 20 == 0 && bossattack == 1){
                    PhysicsRectangle projectile = new PhysicsRectangle(getSource(bossrectangle),null,1,40,20,1);
                    Point2D direction = calculateDirection(bossrectangle,playerrectangle);
                    projectile.setOrientation(getDegree(direction));
                    projectile.setVelocityX(direction.getX());
                    projectile.setVelocityY(direction.getY());
                    p.addObject(projectile);
                }
                bosspattern--;
                if(bosspattern == 0){if(bossattack == 2){arm.setVelocityY(-armvelocity);}bosspattern = -500/armvelocity;}
            }
            if(bosspattern < 0){
                bosspattern++;
                if(bosspattern == 0){bossattack = 0;}
            }
            if((r < 60) && attacksprite == false && bosspattern == 0){
                if(r < 30 && getPlayerPos() == 0 || getPlayerPos() == 2){
                    if(getPlayerPos() == 0){
                        arm = new PhysicsRectangle(new Point2D.Double(83,-500),null,20,500,200,3);
                        arm.setVelocityY(armvelocity);
                        p.addObject(arm);
                        bosspattern = 500/armvelocity;
                        bossattack = 2;
                    }else{
                        arm = new PhysicsRectangle(new Point2D.Double(587,-500),null,20,500,200,2);
                        arm.setVelocityY(armvelocity);
                        p.addObject(arm);
                        bosspattern = 500/armvelocity;
                        bossattack = 2;
                    }
                }else{
                    attacksprite = true; attackspirtecooldown = 30;
                    PhysicsRectangle projectile = new PhysicsRectangle(getSource(bossrectangle),null,1,40,20,1);
                    Point2D direction = calculateDirection(bossrectangle,playerrectangle);
                    projectile.setOrientation(getDegree(direction));
                    projectile.setVelocityX(direction.getX());
                    projectile.setVelocityY(direction.getY());
                    p.addObject(projectile);
                    bosspattern = 59;
                    bossattack = 1;
                }
            }
            if(getPlayerPos() == 0 || getPlayerPos() == 3){
                if(attacksprite){
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[30],bossrectangle.getPosition(),bossrectangle));
                }else{
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[29],bossrectangle.getPosition(),bossrectangle));
                }
            }else if(getPlayerPos() == 1 ||getPlayerPos() == 4){
                if(attacksprite){
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[28],bossrectangle.getPosition(),bossrectangle));
                }else{
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[27],bossrectangle.getPosition(),bossrectangle));
                }
            }else{
                if(attacksprite){
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[32],bossrectangle.getPosition(),bossrectangle));
                }else{
                    g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[31],bossrectangle.getPosition(),bossrectangle));
                }

            }
            if(attacksprite){
                if(attackspirtecooldown > 0){
                    attackspirtecooldown--;
                }else{
                    attacksprite = false;
                }
            }
        }else{
            if(!didkeyspawn){
                soundManager.stopMusic();
                //spawn key
                map = new PhysicsRectangle(new Point2D.Double(415-25,135-25),null,1,50,50,5);
                bossalive = false;
                p.getObjectList().remove(bossrectangle);
                p.getObjectList().add(map);
                didkeyspawn = true;
            }
        }
    }

    private void painthud(){
            if(bossalive){
                g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[34], new Point2D.Double(500,25)));
            }
            if(bosshealthchanged){
                if(bosshealthpercentage <= 0){bosshealthpercentage = 0.01;}
                bosshealthbarfull = bufferedImages[35].getSubimage(0,0,(int)(bufferedImages[35].getWidth()*bosshealthpercentage),bufferedImages[35].getHeight());
                bosshealthchanged = false;
            }
            if(bossalive){
                g.addObject(glistid, new Graphics.GraphicImage(bosshealthbarfull, new Point2D.Double(500,25)));
            }
            if(health >= 1){g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[33],new Point2D.Double(0,gameboardsize-50)));}
            if(health >= 2){g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[33],new Point2D.Double(50,gameboardsize-50)));}
            if(health >= 3){g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[33],new Point2D.Double(100,gameboardsize-50)));}
    }

    private void preventCollision(PhysicsObject of, PhysicsObject with){
        PhysicsObject obj = of.iscolliding();
        if(obj != null){
            if(obj == bossrectangle){
                if(of.getVelocityY() > 0){ //Down
                    if(of.getPosition().getY()+(int)(of.getShape().getBounds2D().getHeight()) == with.getPosition().getY()){
                        of.setVelocityY(0);
                    }
                }else if(of.getVelocityY() < 0){ //Up
                    if(of.getPosition().getY() == with.getPosition().getY()+(int)(with.getShape().getBounds2D().getHeight())){
                        of.setVelocityY(0);
                    }
                }

                if(of.getVelocityX() > 0){ //Right
                    if(of.getPosition().getX()+(int)(of.getShape().getBounds2D().getWidth()) == with.getPosition().getX()){
                        of.setVelocityX(0);
                    }
                }else if(of.getVelocityX() < 0){   //Left
                    if(of.getPosition().getX() == with.getPosition().getX()+(int)(with.getShape().getBounds2D().getWidth())){
                        of.setVelocityX(0);
                    }
                }
            }
        }
    }

    private void damageBoss(double amount){
        if(bossdamagecooldown == 0 && bosshealthpercentage > 0){
            bosshealthpercentage -= amount;
            bossdamagecooldown = 30;
            bosshealthchanged = true;
        }
    }

    private void damagePlayer(double amount){
        if(playerdamagecooldown == 0){
            health -= amount;
            playerdamagecooldown = 60; // 60 ticks/sec -> 30 ticks = 0.5 sec
            soundManager.playSoundID(SoundManager.SOUNDAUA);
        }
    }

    private void checkplayerdamagecollision(){
        if(playerdamagecooldown != 0){playerdamagecooldown--;}
        if(playerattackcooldown != 0){playerattackcooldown--;}
        if(playerattackcooldown == 0){playerattack = false; p.getObjectList().remove(sword); sword = null; addVec = null;}
        if(bossdamagecooldown != 0){bossdamagecooldown--;}
        PhysicsObject obj = playerrectangle.iscolliding();
        if(obj != null){
            if(obj == bossrectangle){
                preventCollision(playerrectangle,bossrectangle);
            }
        }
        if(sword != null && sword.iscolliding() == bossrectangle){
            damageBoss(0.05);
        }
        if(obj != null){
            if(obj == bossrectangle){
                damageBoss(0.01);
                damagePlayer(1);
            }else if(obj.getTextureid() == 1)
            {
                damagePlayer(1);
                p.getObjectList().remove(obj);
            }else if(obj.getTextureid() == 2 || obj.getTextureid() == 3){
                damagePlayer(1);
            }else{}
        }

        if(health <= 0){
            running = false;
            soundManager.playSoundID(SoundManager.SOUNDDIED);
            soundManager.stopMusic();
        }

        if(map != null && map.iscolliding() == playerrectangle){
            showmap = true;
        }

        if(sword != null){
            sword.setPosition(updatgesword());
        }
    }

    private void showmap(){
        if(showmap && animation == 0){
            p.getObjectList().remove(map);
            animation = 390;
            soundManager.playSoundID(SoundManager.SOUNDITEM);
        }
        if(showmap){
            if(animation == 1){
                g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[44],new Point2D.Double(0,0)));
                g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[46],new Point2D.Double(0,0)));
            }else{
                Point2D player = (Point2D) playerrectangle.getPosition().clone();
                g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[43],new Point2D.Double(player.getX(),player.getY()-50)));
                animation--;
            }
        }
    }

    private void repaint(){
        synchronized (g.getObjectlist()){
            frame.repaint();
        }
    }

    private void loop(){
        long tickstarttime = 0;
        long waittime = 0;
        while(true){
            while(running)
            {
                soundManager.tick();
                tickstarttime = System.currentTimeMillis();
                if(pausedcooldown > 0){pausedcooldown--;}
                handleInput();
                if(gamePaused){
                    repaint();
                    g.addObject(0,new Graphics.GraphicImage(bufferedImages[40],new Point2D.Double(216,216)));
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    continue;
                }
                checkplayerdamagecollision();
                p.tick();
                g.getObjectlist().get(glistid).clear();
                bosski();
                for(PhysicsObject object : p.getObjectList())
                {
                    if(object.iscolliding() == border){
                        object.setRemoveflag(true);
                    }
                    if(object == bossrectangle && (!debug)){
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                        continue;
                    }
                    if(object.getTextureid() == -1){
                        if(!(object instanceof PhysicsRectangle)){
                            if(object.iscolliding() == null){g.addObject(glistid, new Graphics.GraphicShape(object.getShape(), Color.GREEN, true));}else{g.addObject(glistid, new Graphics.GraphicShape(object.getShape(), Color.RED, true));}
                        }else{
                            if(object.iscolliding() == null){g.addObject(glistid, new Graphics.GraphicShape(object.getShape(), Color.GREEN, false));}else{g.addObject(glistid, new Graphics.GraphicShape(object.getShape(), Color.RED, false));}
                        }
                    }else if(object.getTextureid() == 0){
                        if(animation == 0){
                            g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[0],object.getPosition(),object));
                        }else{
                            g.addObject(glistid, new Graphics.GraphicImage(bufferedImages[45],object.getPosition(),object));
                        }

                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    }else if(object.getTextureid() == 1){
                            g.addObject(glistid,new Graphics.GraphicImage(rotateImage(bufferedImages[37],object.getOrientation()),object.getPosition(),object));
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    } else if(object.getTextureid() == 2){
                        g.addObject(glistid,new Graphics.GraphicImage(bufferedImages[38],object.getPosition(),object));
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    }
                    else if(object.getTextureid() == 3){
                        g.addObject(glistid,new Graphics.GraphicImage(bufferedImages[39],object.getPosition(),object));
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    }else if(object.getTextureid() == 4){
                        g.addObject(glistid,new Graphics.GraphicImage(rotateSword(bufferedImages[42],object.getOrientation()),object.getPosition(),object,false));
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    }
                    else if(object.getTextureid() == 5){
                        g.addObject(glistid,new Graphics.GraphicImage(rotateSword(bufferedImages[43],object.getOrientation()),object.getPosition(),object,false));
                        if(showhitboxex){
                            if(object.iscolliding() == null){
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.GREEN,false));
                            }else{
                                g.addObject(glistid,new Graphics.GraphicShape(object.getShape(),Color.RED,false));
                            }
                        }
                    }
                }
                removeObjects();
                painthud();
                showmap();
                repaint();
                waittime = 15 - (System.currentTimeMillis()-tickstarttime);
                if(waittime < 0 ){waittime = 0; System.out.println("#Game.Game: Ticks take too long, can't run on 60FPS");}
                try {
                    Thread.sleep(waittime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            float x = 100;
            float alphastep = 1.0F/x;
            for(int i = 0; i < x; i++){
                long time = System.currentTimeMillis();
                repaint();
                soundManager.tick();
                Graphics.GraphicImage gameover = new Graphics.GraphicImage(bufferedImages[36], new Point2D.Double(0,0),(i*alphastep));
                g.addObject(glistid, gameover);
                time = System.currentTimeMillis()-time;
                if(time < 15){
                    try {
                        Thread.sleep(15-time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    i = i+((int)(time/15));
                }
            }
            while(!rerun){
                soundManager.tick();
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handleKeyInput();
            }
            //System.out.println("Resetting");
            reset();
            //System.out.println("Setting up again");
            setup();
            //System.out.println("Reset");
        }
    }

    private void removeObjects(){
        for(Iterator<PhysicsObject> it = p.getObjectList().iterator(); it.hasNext();){
            PhysicsObject obj = it.next();
            if(obj.isRemoveflag()){
                //System.out.println("Destroy");
                it.remove();
                obj.setRemoveflag(false);
            }
        }
    }

    private void setupBackground(BufferedImage [] [] background){
        //Setting Corners
        background[0][0] = bufferedImages[1];
        background[0][12] = bufferedImages[21];
        background[12][0] = bufferedImages[5];
        background[12][12] = bufferedImages[25];
        int r = 0;
        //Setting Edges
        //Upper
        BufferedImage option0 = bufferedImages[6];
        BufferedImage option1 = bufferedImages[11];
        BufferedImage option2 = bufferedImages[16];
        setupRow(0,background,option0,option1,option2);
        //Lower
        option0 = bufferedImages[20];
        option1 = bufferedImages[15];
        option2 = bufferedImages[10];
        setupRow(12,background,option0,option1,option2);
        //Left
        option0 = bufferedImages[4];
        option1 = bufferedImages[3];
        option2 = bufferedImages[2];
        setupColumn(0,background,option0,option1,option2);
        //Right
        option0 = bufferedImages[22];
        option1 = bufferedImages[23];
        option2 = bufferedImages[24];
        setupColumn(12,background,option0,option1,option2);
        //Inner
        option0 = bufferedImages[14];
        option1 = bufferedImages[9];
        option2 = bufferedImages[19];
        for(int i = 1; i < 12; i++){
            setupRow(i,background,option0,option1,option2);
        }
    }

    private Point2D getSource(PhysicsObject from){
        Point2D source = (Point2D) from.getPosition().clone();
        source.setLocation(source.getX()+from.getShape().getBounds2D().getWidth()/2,source.getY()+from.getShape().getBounds2D().getHeight());
        return source;
    }

    private double getDegree(double x1, double y1, double x2, double y2){
        double m1 = x1/y1, m2 = x2/y2;
        double d = Math.toDegrees(Math.atan((m1-m2)/(1+m1*m2)));
        return 360-d;
    }

    private double getDegree(Point2D m){
        double x = m.getX(), y = m.getY();
        double d = Math.abs(Math.toDegrees(Math.atan(m.getX()/m.getY())));
        if(x >= 0){
            //left
            if(y >= 0){
                //lower
                return 180-d;
            }else{
                //upper
                return d;
            }
        }else{
            //right
            if(y >= 0){
                //lower
                return 180+d;
            }else{
                //upper
                return 360-d;
            }
        }
    }

    private double getDegree(Point2D source, Point2D target){
        double x = target.getX()-source.getX(), y = target.getY()-source.getY();
        Point2D m = mulVector(normVector(new Point2D.Double(x,y)),5);
        return getDegree(m);
    }

    private Point2D calculateDirection(PhysicsObject from, PhysicsObject to){
        Point2D source = getSource(from);
        Point2D target = (Point2D) to.getPosition().clone();
        target.setLocation(to.getPosition().getX()+to.getShape().getBounds2D().getWidth()/2,target.getY());
        return mulVector(normVector(new Point2D.Double(target.getX()-source.getX(),target.getY()-source.getY())),5);    //Get vector on length 5
    }

    private Point2D calculateDirection(Point2D source, Point2D target){                         //Points have to be in the middle of their represented objects
        return new Point2D.Double(target.getX()-source.getX(),target.getY()-source.getY());
    }

    public static Point2D mulVector(Point2D vector, double factor){
        return new Point2D.Double(vector.getX()*factor,vector.getY()*factor);
    }

    private Point2D normVector(Point2D vector){
        double factor = 1/Math.sqrt(Math.pow(vector.getX(),2)+Math.pow(vector.getY(),2));
        return new Point2D.Double(vector.getX()*factor,vector.getY()*factor);
    }

    private double lengthOf(Point2D vector){
        return Math.sqrt(vector.getX()*vector.getX()+vector.getY()*vector.getY());
    }

    private Point2D addVector(Point2D v1, Point2D v2){
        return new Point2D.Double(v1.getX()+v2.getX(),v1.getY()+v2.getY());
    }

    private Point2D subVector(Point2D v1, Point2D v2){
        return new Point2D.Double(v1.getX()-v2.getX(),v1.getY()-v2.getY());
    }

    private BufferedImage rotateImage(BufferedImage image, double angle){
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(angle), image.getWidth()/2,image.getHeight()/2);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image,null);
    }

    private BufferedImage rotateSword(BufferedImage image, double angle){
        AffineTransform tx = AffineTransform.getRotateInstance(Math.toRadians(angle), image.getWidth(),image.getHeight());
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        return op.filter(image,null);
    }

    private void setupRow(int currentrow, BufferedImage [] [] background, BufferedImage option0, BufferedImage option1, BufferedImage option2){
        Random random = new Random();
        int r = 0;
        for(int i = 1; i < 12; i++){
            r = random.nextInt(7);
            switch(r){
                case 0:{background[currentrow][i] = option0;break;}
                case 1:{background[currentrow][i] = option1;break;}
                case 2:{background[currentrow][i] = option2;break;}
                case 3:{background[currentrow][i] = option0;break;}
                case 4:{background[currentrow][i] = option0;break;}
                case 5:{background[currentrow][i] = option0;break;}
                case 6:{background[currentrow][i] = option0;break;}
            }
        }
    }

    private void setupColumn(int currentcolumn, BufferedImage [] [] background, BufferedImage option0, BufferedImage option1, BufferedImage option2){
        Random random = new Random();
        int r = 0;
        for(int i = 1; i < 12; i++){
            r = random.nextInt(5);
            switch(r){
                case 0:{background[i][currentcolumn] = option0;break;}
                case 1:{background[i][currentcolumn] = option1;break;}
                case 2:{background[i][currentcolumn] = option2;break;}
                case 3:{background[i][currentcolumn] = option0;break;}
                case 4:{background[i][currentcolumn] = option0;break;}
                case 5:{background[i][currentcolumn] = option0;break;}
                case 6:{background[i][currentcolumn] = option0;break;}
            }
        }
    }

    private void handleInput(){
        handleKeyInput();
        handleMouseInput();
    }

    private void handleKeyInput(){
        if(kl.isKeypressed()){
            boolean [] keys = kl.getKeys();
            if(keys[0]){ playerrectangle.setVelocityY(-5); }else if(keys[2]){ playerrectangle.setVelocityY(5); }else{playerrectangle.setVelocityY(0);}
            if(keys[1]){ playerrectangle.setVelocityX(-5); }else if(keys[3]){ playerrectangle.setVelocityX(5); }else{playerrectangle.setVelocityX(0);}
            if(keys[4]){
                playerattack();
            }
            if(keys[5]){
                if(pausedcooldown <= 0){
                    if(running){gamePaused = !gamePaused; pausedcooldown = 30;}
                }
                if(!running){rerun = true;}
            }

            Point2D pos = playerrectangle.getPosition();
            if(pos.getX() <= 0){
                if(playerrectangle.getVelocityX() < 0){playerrectangle.setVelocityX(0);}
            }
            if(pos.getY() <= 0){
                if(playerrectangle.getVelocityY() < 0){playerrectangle.setVelocityY(0);}
            }
            if(pos.getX() >= gameboardsize - playerrectangle.getWidth()){
                if(playerrectangle.getVelocityX() > 0){playerrectangle.setVelocityX(0);}
            }
            if(pos.getY() >= gameboardsize- playerrectangle.getHeight()){
                if(playerrectangle.getVelocityY() > 0){playerrectangle.setVelocityY(0);}
            }
        }
    }

    private void playerattack(){
        if(playerattack == false){
            playerattack = true;
            playerattackcooldown = 30;
            sword = new PhysicsRectangle(new Point2D.Double(0,0),null,1,80,27,4);
            sword.setPosition(calculatesword());
            p.getObjectList().add(sword);
            soundManager.playSoundID(SoundManager.SOUNDSWORD);
        }
    }

    private Point2D updatgesword(){
        if(addVec != null){
            Point2D swordpos = (Point2D) playerrectangle.getPosition().clone();
            swordpos = new Point2D.Double(swordpos.getX()+(playerrectangle.getWidth()/2),swordpos.getY()+(playerrectangle.getHeight()/2));
            swordpos = addVector(swordpos,addVec);
            return swordpos;
        }
        return null;
    }

    private Point2D calculatesword(){
        final int swordwidth = 27;
        final int swordheight = 80;
        final int distancefromplayercentertosword = 145;
        Point2D swordpos = (Point2D) playerrectangle.getPosition().clone();
        swordpos = new Point2D.Double(swordpos.getX()+(playerrectangle.getWidth()/2),swordpos.getY()+(playerrectangle.getHeight()/2));
        double degree = getDegree(swordpos,mouseMove_listener.getMousePos());
        double m = Math.toRadians(degree);
        Point2D slope = new Point2D.Double(Math.sin(m),Math.cos(m));
        addVec = mulVector(slope,distancefromplayercentertosword);
        if(degree < 90 ){
            addVec = new Point2D.Double(addVec.getX(),-addVec.getY());
        }else if(degree < 180){
            addVec = new Point2D.Double(addVec.getX(),-addVec.getY());
        }else if(degree < 270){
            addVec = new Point2D.Double(addVec.getX(),-addVec.getY());
        }else if(degree < 360){
            addVec = new Point2D.Double(addVec.getX(),-addVec.getY());
        }
        sword.setOrientation(degree);
        swordpos = addVector(swordpos,addVec);
        return swordpos;
    }

    private void handleMouseInput(){
        g.setCursor(new Graphics.GraphicImage(bufferedImages[41],new Point2D.Double(mouseMove_listener.getMousex()-bufferedImages[41].getWidth()/2,mouseMove_listener.getMousey()-bufferedImages[41].getHeight()/2)));
        if(mouse_listener.isMousePressed()){
            //Do smth
            //System.out.println("X: " + mouseMove_listener.getMousex() + " Y: " + mouseMove_listener.getMousey());
            playerattack();
            mouse_listener.reset();
        }
    }

}
