import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Main extends JFrame {
	private static JTextArea textArea;
	private static JScrollPane scrollPane;
	private static int nextScene=0;
	private static boolean changeOccured=false;
	private static JPanel buttonsPanel = new JPanel();

	public Main() {

		//window definition
		setSize(500,500);
		setTitle("Projet TLA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridBagLayout());
		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e){
			   System.exit(0);
			}
		 };
		addWindowListener(l);

		//constraints on the size of the rows
		GridBagConstraints gbcTextArea = new GridBagConstraints();
        gbcTextArea.gridx = 0;
        gbcTextArea.gridy = 0;
        gbcTextArea.weightx = 0.7; //width
        gbcTextArea.weighty = 0.5; //height
        gbcTextArea.fill = GridBagConstraints.BOTH;

		GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 1;
        gbcButtons.weightx = 0.7; //width
        gbcButtons.weighty = 0.5; //height
        gbcButtons.fill = GridBagConstraints.BOTH;

		//textarea definition
        textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);

        /*int preferredWidth = (int) (getSize().width * 0.7);
        int preferredHeight = (int) (getSize().height * 0.7);
        textArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));*/

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		//button panel definition
		//creation of the button panel
		buttonsPanel.setLayout(new GridBagLayout());

		//components added to the window
        getContentPane().add(scrollPane,gbcTextArea);
		getContentPane().add(buttonsPanel,gbcButtons);

		//this is to readjust the size of the elements when the window's size changes
		//TODO
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustWindowSize();
            }
        });

    	setLocationRelativeTo(null);
        setVisible(true);

	}
	 
	public static JTextArea getTextArea(){
		return textArea;
	}

	public static JScrollPane getScrollPane(){
		return scrollPane;
	}

	public static JPanel getButtonPanel(){
		return buttonsPanel;
	}

	private void adjustWindowSize(){
        redraw(this);
	}

	

	private static void run(List<Token> lt) {

		//WINDOW DEFINITION
		JFrame window = new Main();

		//GAME PARAMETERS
		AnalyseSyntaxique ansyn = new AnalyseSyntaxique();
		Game game = ansyn.analyse(lt);
		System.out.println(game);
		/*boolean inGame=true;
		List<JButton> buttonList = new ArrayList<>();
		boolean endBool=true;

		//FIRST SCENE
		Scene s = game.getScenes().get(0);
		String sceneText=s.getText();
		getTextArea().setText(sceneText);
		

		//GRID CONSTRAINT DEFINITION FOR BUTTON PANEL
		GridBagConstraints gdcSecondColumn = new GridBagConstraints();
        gdcSecondColumn.gridx = 1;
        gdcSecondColumn.gridy = 0;
        gdcSecondColumn.fill = GridBagConstraints.HORIZONTAL;

		//FIRST CHOICES
		for (Choice choice : game.getChoices()) {
			if (choice.getRelation()==s.getId()) {

				JButton button = new JButton(choice.getText());
				button.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						nextScene=choice.getNextScene();
						changeOccured=true;
						for (JButton jButton : buttonList) {
							buttonsPanel.remove(jButton);
						}
					}
       			});

        		buttonsPanel.add(button,gdcSecondColumn);
				gdcSecondColumn.gridy++;
				buttonList.add(button);
			}
		}
		gdcSecondColumn.gridy=0;

		redraw(window);

		//game loop
		while (inGame) {
			System.out.println(changeOccured);
			if (changeOccured) {
				System.out.println("Change occured");
				for (Scene scene : game.getScenes()) {
					if (scene.getId()==nextScene) {
						s=scene;
						sceneText=s.getText();
						getTextArea().setText(sceneText);
						break;
					}
				}
				for (Choice choice : game.getChoices()) {
					if (choice.getRelation()==s.getId()) {
						JButton button = new JButton(choice.getText());
				
						button.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								nextScene=choice.getNextScene();
								changeOccured=true;
								for (JButton jButton : buttonList) {
									buttonsPanel.remove(jButton);
								}
							}
						});
						buttonsPanel.add(button,gdcSecondColumn);
						gdcSecondColumn.gridy++;
						buttonList.add(button);
					}
				}
				gdcSecondColumn.gridy=0;
				changeOccured=false;
				redraw(window);
			}

			if (s.isEnd() && endBool) {
				JButton endButton = new JButton("Sortir du jeu");
				endButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								window.dispose();
							}
				});
				inGame=false;
				buttonsPanel.add(endButton);
				redraw(window);
				endBool=false;
			}
		}*/
	}

	private static void redraw(JFrame window) {
		textArea.revalidate();
        scrollPane.revalidate();
		buttonsPanel.revalidate();
		window.revalidate();
		window.repaint();
	}

	public static void main(String[] args) throws IllegalCharacterException, FileNotFoundException {
		AnalyseLexicale al = new AnalyseLexicale();
		File textFile = new File("src/adventures/test.txt");
		Scanner sc = new Scanner(textFile);
		String txt = "";
		while (sc.hasNextLine()) {
			txt+=sc.nextLine();
			txt+="\n";
		}
		sc.close();
		List<Token> lt = al.analyse(txt);
		for (Token token : lt) {
			System.out.println(token);
		}
		run(lt);
	}
}