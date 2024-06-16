package com.example.ldp_marcorui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javafx.scene.image.Image;

/**
 * Classe que controla a lógica do jogo.
 */
public class Game implements Runnable{

    @FXML
    private TextField nameInput;

    @FXML
    private TextField ipInput;

    @FXML
    private TextField portInput;

    @FXML
    private Button btnPlay;

    @FXML
    private Button btnRules;

    @FXML
    private Button btnExit;

    @FXML
    private Button btnExitGame;

    @FXML
    private Button btnExitToHome;

    @FXML
    private Button btnPlayGame;

    @FXML
    private Button btnRoll;

    @FXML
    private Button btnAlertExitYes;

    @FXML
    private Button btnAlertExitNo;

    @FXML
    private Button btnPlayAgain;

    @FXML
    private ImageView snakeId;

    @FXML
    private ImageView ladderId;

    @FXML
    private ImageView dice;

    @FXML
    private ImageView playerOneColor;

    @FXML
    private ImageView playerTwoColor;

    @FXML
    private ImageView table;

    @FXML
    private ImageView snake1;

    @FXML
    private ImageView snake2;

    @FXML
    private ImageView snake3;

    @FXML
    private ImageView ladder1;

    @FXML
    private ImageView ladder2;

    @FXML
    private ImageView ladder3;

    @FXML
    private Label titleHomeId;

    @FXML
    private Label titleRuleId;

    @FXML
    private Label textRuleId;

    @FXML
    private Label titlePlayerId;

    @FXML
    private Label alertExit;

    @FXML
    private Label alert;

    @FXML
    private Label alertOpponentLeft;

    @FXML
    private Label titleGameId;

    @FXML
    private Text textDiceValue;

    @FXML
    private Text textBeforeValue;

    @FXML
    private HBox boxColorId;

    @FXML
    private VBox boxInputId;

    @FXML
    private ToggleGroup group;

    ArrayList<Tile> board = new ArrayList<>();

    Player playerOne;
    Player playerTwo;

    public String selectedColor;
    private String ip;
    private int port;
    private Thread thread;
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private ServerSocket serverSocket;
    private boolean isAccepted = false;
    private boolean isPlayerOneTurn = false;
    private boolean isPlayerTwoTurn = true;
    private boolean playerLeft = false;
    public Image imgPlayerOne;
    public Image imgPlayerTwo;

    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), ev -> {
        textBeforeValue.setFill(Color.rgb(0, 0, 255));
        dice.setImage(Dice.getSides().get(0));
        textDiceValue.setVisible(false);
        textBeforeValue.setLayoutX(511);
        textBeforeValue.setText("Jogada oponente!");
    }));

    public Game() {
        timeline.setCycleCount(1);
    }

    /**
     * Define a ação a ser executada quando o botão "Jogar" é clicado.
     *
     * @param event O evento de clique do botão "Jogar".
     */
    @FXML
    void playClicked(ActionEvent event) {
        snakeId.setVisible(false);
        ladderId.setVisible(false);
        titleHomeId.setVisible(false);
        btnPlay.setVisible(false);
        btnRules.setVisible(false);
        btnExit.setVisible(false);
        titlePlayerId.setVisible(true);
        btnExitToHome.setVisible(true);
        boxColorId.setVisible(true);
        boxInputId.setVisible(true);
        btnPlayGame.setVisible(true);
    }

    /**
     * Define a ação a ser executada quando o botão "Regras" é clicado.
     */
    @FXML
    void rulesClicked() {
        snakeId.setVisible(false);
        ladderId.setVisible(false);
        titleHomeId.setVisible(false);
        btnPlay.setVisible(false);
        btnRules.setVisible(false);
        btnExit.setVisible(false);
        titleRuleId.setVisible(true);
        btnExitToHome.setVisible(true);
        textRuleId.setVisible(true);
    }

    /**
     * Define a ação a ser executada quando o botão "Sair" é clicado.
     */
    @FXML
    void exitProgramClicked() {
        System.exit(0);
    }

    /**
     * Define a ação a ser executada quando o botão "Sair" do jogo é clicado.
     *
     * @param event O evento de clique do botão "Sair" do jogo.
     */
    @FXML
    void exitGameClicked(ActionEvent event){
        alertExit.setVisible(true);
        btnAlertExitNo.setVisible(true);
        btnAlertExitYes.setVisible(true);
        btnRoll.setDisable(true);
        btnExit.setDisable(true);
    }

    /**
     * Define a ação a ser executada quando o botão "Sair para o menu" é clicado.
     */
    @FXML
    void exitHomeClicked() {
        snakeId.setVisible(true);
        ladderId.setVisible(true);
        titleHomeId.setVisible(true);
        btnPlay.setVisible(true);
        btnRules.setVisible(true);
        btnExit.setVisible(true);
        titleRuleId.setVisible(false);
        btnExitToHome.setVisible(false);
        textRuleId.setVisible(false);
        titlePlayerId.setVisible(false);
        boxColorId.setVisible(false);
        boxInputId.setVisible(false);
        btnPlayGame.setVisible(false);
        alert.setVisible(false);
    }

    /**
     * Estabelece a conexão com o servidor.
     *
     * @return true se a conexão for estabelecida com sucesso, false caso contrário.
     */
    private boolean connect() {
        try {
            socket = new Socket(ip, port);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF(selectedColor);  // Envia a cor selecionada pelo cliente
            String serverColor = dis.readUTF();  // Recebe a cor do servidor

            imgPlayerOne = new Image("/img/" + selectedColor + ".png");
            playerOneColor.setImage(imgPlayerOne);
            playerOneColor.setVisible(true);

            imgPlayerTwo = new Image("/img/" + serverColor + ".png");
            playerTwoColor.setImage(imgPlayerTwo);
            playerTwoColor.setVisible(true);

            isAccepted = true;
            isPlayerTwoTurn = true;
        } catch (IOException e) {
            System.out.println("Não foi possível connectar ao endereço: " + ip + ": " + port + ".");
            return false;
        }
        System.out.println("Conexão ao servidor feita com sucesso!");
        return true;
    }

    /**
     * Aguarda a solicitação do cliente.
     */
    private void serverRequest() {
        Socket socket = null;
        try {
            socket = serverSocket.accept();
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            String clientColor = dis.readUTF();  // Recebe a cor do cliente

            playerTwo = new Player(nameInput, playerTwoColor);
            imgPlayerTwo = new Image("/img/" + clientColor + ".png");
            playerTwoColor.setImage(imgPlayerTwo);
            playerTwoColor.setVisible(true);

            dos.writeUTF(selectedColor);  // Envia a cor selecionada pelo servidor

            playerTwo.setTilePlayer(board.get(0));
            playerTwo.getColor().setLayoutX(board.get(0).getX());
            playerTwo.getColor().setLayoutY(board.get(0).getY());
            textBeforeValue.setLayoutX(515);
            textBeforeValue.setText("Sua vez de jogar!");

            isAccepted = true;
            System.out.println("O OPONENTE ENTROU NO JOGO");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inicia o servidor do jogo.
     * O servidor é iniciado com o endereço IP e a porta fornecidos.
     *
     * @throws IOException se ocorrer um erro de entrada/saída ao iniciar o servidor.
     */
    private void startServer() {
        try {
            serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
        } catch (Exception e) {
            e.printStackTrace();
        }
        isPlayerOneTurn = true;
        isPlayerTwoTurn = false;
    }

    /**
     * Inicia o tabuleiro do jogo.
     * Cria as 100 peças do tabuleiro e define as posições iniciais e finais das cobras e escadas.
     */
    private void startBoard() {
        // Coordenadas iniciais
        int x = 0;
        int y = 360;
        int operation = 1;

        // Loop para criar as 100 peças do tabuleiro
        for (int i = 1; i <= 100; i++) {
            // Adiciona uma nova peça ao tabuleiro
            board.add(new Tile(i, x * 40, y));

            // Verifica se chegou ao final de uma linha (10 peças)
            if (i % 10 == 0) {
                // Se a linha é par (0, 20, 40, ...), inicie uma nova linha à esquerda
                if (i % 20 == 0) {
                    x = 0;
                    y -= 40;
                    operation = 1;
                }
                // Se a linha é ímpar (10, 30, 50, ...), inicie uma nova linha à direita
                else {
                    x = 9;
                    y -= 40;
                    operation = -1;
                }
            } else {
                // Move a posição x na direção atual
                x += operation;
            }
        }

        // Localização das cobras e escadas
        EndTile cobra1 = new EndTile(board.get(2));
        board.get(36).setSnake(cobra1);
        EndTile cobra2 = new EndTile(board.get(9));
        board.get(27).setSnake(cobra2);
        EndTile cobra3 = new EndTile(board.get(57));
        board.get(95).setSnake(cobra3);
        EndTile escada1 = new EndTile(board.get(34));
        board.get(4).setLadder(escada1);
        EndTile escada2 = new EndTile(board.get(87));
        board.get(53).setLadder(escada2);
        EndTile escada3 = new EndTile(board.get(78));
        board.get(42).setLadder(escada3);
    }

    /**
     * Método que muda o lado do dado.
     *
     * @param value O valor obtido no lançamento do dado.
     */
    void changeDice(int value) {
        // Define o valor de texto do dado
        textDiceValue.setText(String.valueOf(value));

        // Verifica se o valor do dado está dentro dos limites aceitáveis
        if (value >= 1 && value <= 6) {
            // Define a imagem do dado com base no valor
            dice.setImage(Dice.getSides().get(value));
        } else {
            // Lança uma exceção ou manipula o erro de valor inválido
            throw new IllegalArgumentException("Valor do dado deve estar entre 1 e 6: " + value);
        }
    }

    /**
     * Método que move o jogador para uma nova peça do tabuleiro.
     *
     * @param diceValue O valor obtido no lançamento do dado.
     * @param player O jogador que está realizando a jogada.
     */
    void changeTile(int diceValue, Player player) {
        int previousTileIndex = board.indexOf(player.getTilePlayer());

        // Verifica se o jogador está na primeira casa e não rolou 6
        if (player.getTilePlayer().getId() == 1 && diceValue != 6 && !playerOne.getCanAdvance() && !playerTwo.getCanAdvance()) {
            player.getColor().setLayoutX(board.get(0).getX());
            player.getColor().setLayoutY(board.get(0).getY());
            textBeforeValue.setLayoutX(456);
            textBeforeValue.setText("Precisas do dado 6 para começar!");
            textDiceValue.setVisible(false);
            return;
        }

        // Atualiza a regra para permitir que ambos os jogadores avancem se qualquer um tirar 6
        if (diceValue == 6) {
            playerOne.setCanAdvance(true);
            playerTwo.setCanAdvance(true);
        }

        // Move o jogador para a nova casa, se não ultrapassar o limite do tabuleiro
        if (previousTileIndex + diceValue > 99) {
            player.setTilePlayer(board.get(previousTileIndex));
        } else {
            player.setTilePlayer(board.get(previousTileIndex + diceValue));
        }

        // Verifica se o jogador encontrou uma cobra ou uma escada
        if (player.getTilePlayer().getSnake() != null) {
            player.setTilePlayer(player.getTilePlayer().getSnake().endTile);
        } else if (player.getTilePlayer().getLadder() != null) {
            player.setTilePlayer(player.getTilePlayer().getLadder().endTile);
        }

        // Atualiza a posição do jogador no tabuleiro
        player.getColor().setLayoutX(player.getTilePlayer().getX());
        player.getColor().setLayoutY(player.getTilePlayer().getY());

        // Verifica se o jogador alcançou a última casa e ganhou o jogo
        if (player.getTilePlayer().getId() == 100) {
            isPlayerOneTurn = false;
            btnPlay.setVisible(false);
            btnRoll.setVisible(false);
            btnPlayAgain.setVisible(true);
            btnExit.setLayoutX(514);
            btnExit.setLayoutY(216);
            dice.setVisible(false);
            textDiceValue.setVisible(false);
            textBeforeValue.setText("GANHOU!");
        }
    }

    /**
     * Realiza a jogada do oponente.
     * Aguarda a leitura do valor do dado enviado pelo oponente e realiza a movimentação de acordo com o valor recebido.
     * Se o valor lido for -1, indica que o oponente deixou o jogo e chama o método {@link #handleOpponentLeft()}.
     *
     * @throws InterruptedException se a thread for interrompida enquanto espera a resposta do oponente.
     */
    private void opponentMove() {
        System.out.println("");
        int enemyDiceValue = 0;

        if (dis != null) {
            try {
                enemyDiceValue = dis.readInt();
                if (enemyDiceValue == -1) {
                    handleOpponentLeft();
                    return;
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isPlayerOneTurn && dis != null) {
            timeline.stop();
            if (enemyDiceValue == 7) {
                handleOpponentWin();
            } else {
                handleOpponentMove(enemyDiceValue);
            }
        }
    }

    /**
     * Lida com a saída do oponente do jogo.
     * Mostra uma mensagem de alerta indicando que o oponente deixou o jogo, aguarda por 2 segundos e atualiza a interface para permitir uma nova partida.
     *
     * @throws InterruptedException se a thread for interrompida enquanto aguarda a execução da pausa.
     */
    private void handleOpponentLeft() throws InterruptedException {
        alertOpponentLeft.setVisible(true);
        TimeUnit.MILLISECONDS.sleep(2000);
        alertOpponentLeft.setVisible(false);
        textBeforeValue.setVisible(false);
        textDiceValue.setVisible(false);
        btnPlay.setVisible(false);
        btnRoll.setVisible(false);
        btnExit.setLayoutX(514);
        btnExit.setLayoutY(216);
        btnPlayAgain.setVisible(true);
        dice.setVisible(false);
        textDiceValue.setVisible(false);
        playerLeft = true;
    }

    /**
     * Lida com a vitória do oponente.
     * Move a peça do jogador dois para a última casa do tabuleiro, oculta os botões de jogo e mostra uma mensagem a dizer que o jogador foi derrotado.
     */
    private void handleOpponentWin() {
        playerTwo.getColor().setLayoutX(board.get(99).getX());
        playerTwo.getColor().setLayoutY(board.get(99).getY());
        btnPlay.setVisible(false);
        btnRoll.setVisible(false);
        btnExit.setLayoutX(514);
        btnExit.setLayoutY(216);
        btnPlayAgain.setVisible(true);
        dice.setVisible(false);
        textDiceValue.setVisible(false);
        textBeforeValue.setLayoutX(517);
        textBeforeValue.setText("Perdeste o jogo!");
    }

    /**
     * Lida com o movimento do oponente.
     * Atualiza o dado com o valor lançado pelo oponente, move a peça do jogador dois de acordo com o valor do dado, mostra uma mensagem indicando que o oponente jogou os dados e atualiza a interface para a vez do jogador um.
     *
     * @param enemyDiceValue O valor do dado lançado pelo oponente.
     */
    private void handleOpponentMove(int enemyDiceValue) {
        changeDice(enemyDiceValue);
        changeTile(enemyDiceValue, playerTwo);
        textBeforeValue.setLayoutX(470);
        textDiceValue.setLayoutX(670);
        textDiceValue.setVisible(true);
        textBeforeValue.setText("Oponente atirou os dados:");
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        textBeforeValue.setLayoutX(540);
        textDiceValue.setVisible(false);
        dice.setImage(Dice.getSides().get(0));
        textBeforeValue.setText("A tua vez!");
        isPlayerOneTurn = true;
    }

    /**
     * Lida com a seleção da cor do jogador.
     * Define a cor selecionada pelo jogador e atualiza a interface de acordo com a escolha.
     *
     * @param event O evento de seleção de cor.
     */
    @FXML
    void handleColorSelection(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) event.getSource();
        selectedColor = selectedRadioButton.getId();
        System.out.println("Selected color: " + selectedColor);
    }

    /**
     * Lida com o evento de clique no botão "Jogar".
     * Verifica se os campos de IP e porta estão preenchidos e se uma cor foi selecionada. Se todas as condições forem atendidas, inicia a conexão e o servidor, inicia também o tabuleiro e atualiza a interface do jogo.
     *
     * @param event O evento de clique no botão "Jogar".
     */
    @FXML
    void playGameClicked(ActionEvent event) {
        if(ipInput.getText().isEmpty() || portInput.getText().isEmpty()) {
            alert.setVisible(true);
        } else if (group.getSelectedToggle() == null) {
            alert.setVisible(true);
        } else {
            alert.setVisible(false);
            ip = ipInput.getText();
            if(portInput.getText().length() > 5) {
                alert.setPrefWidth(170);
                alert.setText("A porta é inválida!");
                alert.setVisible(true);
            } else {
                port = Integer.parseInt(portInput.getText());
            }
            if(port < 1 || port > 65535) {
                alert.setPrefWidth(170);
                alert.setText("A porta é inválida!");
                alert.setVisible(true);
            } else {
                try {
                    InetAddress.getByName(ip);
                    if(!connect()) startServer();
                    thread = new Thread((Runnable) this, "Game");
                    thread.start();

                    startBoard();

                    //Definições de interface
                    titleGameId.setVisible(true);
                    snake1.setVisible(true);
                    snake2.setVisible(true);
                    snake3.setVisible(true);
                    ladder1.setVisible(true);
                    ladder2.setVisible(true);
                    ladder3.setVisible(true);
                    table.setVisible(true);
                    alert.setVisible(false);
                    titlePlayerId.setVisible(false);
                    btnExitToHome.setVisible(false);
                    boxColorId.setVisible(false);
                    boxInputId.setVisible(false);
                    btnPlayGame.setVisible(false);
                    btnRoll.setVisible(true);
                    btnExitGame.setVisible(true);
                    textBeforeValue.setVisible(true);
                    dice.setVisible(true);
                    dice.setImage(Dice.getSides().get(0));
                    ipInput.setVisible(false);
                    portInput.setVisible(false);

                    if(!isPlayerTwoTurn && !isAccepted) {
                        textBeforeValue.setText("À espera do oponente...");
                        textBeforeValue.setLayoutX(491);
                        playerOne = new Player(nameInput, playerOneColor);
                        imgPlayerOne = new Image("/img/" + selectedColor + ".png");
                        playerOneColor.setImage(imgPlayerOne);
                        playerOneColor.setVisible(true);
                        playerOne.setTilePlayer(board.get(0));
                        playerOne.getColor().setLayoutX(board.get(0).getX());
                        playerOne.getColor().setLayoutY(board.get(0).getY());
                    } else {
                        if(isPlayerTwoTurn) {
                            textBeforeValue.setLayoutX(500);
                            textBeforeValue.setText("Jogada do oponente!");
                        } else {
                            textBeforeValue.setLayoutX(504);
                            textBeforeValue.setText("É a tua vez de jogar!");
                        }
                        playerTwo = new Player(nameInput, playerTwoColor);
                        playerTwoColor.setVisible(true);
                        playerOne = new Player(nameInput, playerOneColor);
                        playerOneColor.setVisible(true);
                        playerOne.setTilePlayer(board.get(0));
                        playerTwo.setTilePlayer(board.get(0));

                        playerOne.getColor().setLayoutX(board.get(0).getX());
                        playerOne.getColor().setLayoutY(board.get(0).getY());
                        playerTwo.getColor().setLayoutX(board.get(0).getX());
                        playerTwo.getColor().setLayoutY(board.get(0).getY());
                    }
                } catch(UnknownHostException e) {
                    alert.setText("O IP é inválido!");
                    alert.setVisible(true);
                }
            }
        }
    }

    /**
     * Lida com o evento de clique no botão "Jogar outra vez?".
     * Esconde todos os elementos do jogo, para que um novo jogo possa ser iniciado. Fecha as sockets e reseta as variáveis relacionadas ao estado do jogo.
     *
     * @param event O evento de clique no botão "Jogar outra vez".
     */
    @FXML
    void playAgainClicked(ActionEvent event) {
        titleGameId.setVisible(false);
        snake1.setVisible(false);
        snake2.setVisible(false);
        snake3.setVisible(false);
        ladder1.setVisible(false);
        ladder2.setVisible(false);
        ladder3.setVisible(false);
        textBeforeValue.setVisible(false);
        textDiceValue.setVisible(false);
        dice.setVisible(false);
        btnPlayAgain.setVisible(false);
        btnRoll.setVisible(false);
        table.setVisible(false);
        playerOneColor.setVisible(false);
        playerTwoColor.setVisible(false);
        timeline.stop();

        if(serverSocket != null) {
            try {
                serverSocket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null) {
            try {
                socket.close();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        dis = null;
        dos = null;

        playerOne = null;
        playerTwo = null;
        isAccepted = false;
        isPlayerOneTurn = false;
        isPlayerTwoTurn = true;

        titlePlayerId.setVisible(true);
        boxColorId.setVisible(true);
        boxInputId.setVisible(true);
        btnPlayGame.setVisible(true);
        thread.stop();
    }

    /**
     * Lida com o evento de clique no botão "Não" do alerta de saída.
     * Esconde o alerta de saída e permite o uso dos botões "Rodar" e "Sair".
     *
     * @param event O evento de clique no botão "Não" do alerta de saída.
     */
    @FXML
    void noClicked(ActionEvent event) {
        alertExit.setVisible(false);
        btnAlertExitNo.setVisible(false);
        btnAlertExitYes.setVisible(false);
        btnRoll.setDisable(false);
        btnExit.setDisable(false);
    }

    /**
     * Lida com o evento de clique no botão "Sim" do alerta de saída.
     * Fecha a conexão com o servidor e fecha o programa.
     *
     * @param event O evento de clique no botão "Sim" do alerta de saída.
     */
    @FXML
    void yesClicked(ActionEvent event) {
        if(dos != null) {
            try {
                dos.writeInt(-1);
                dos.flush();
            } catch(IOException e1) {
                e1.printStackTrace();
            }
        }
        Platform.exit();
        System.exit(0);
    }

    /**
     * Lida com o evento de clique no botão "Rodar".
     * Executa a jogada do jogador atual, atualiza o dado, muda a posição do jogador no tabuleiro e envia a jogada para o oponente.
     *
     * @param event O evento de clique no botão "Rodar".
     */
    @FXML
    void rollClicked(ActionEvent event) {
        //isAccepted = true;
        if(isAccepted) {
            Player player = playerOne;
            //isPlayerOneTurn = true;
            if(isPlayerOneTurn) {
                textDiceValue.setVisible(true);
                textBeforeValue.setLayoutX(550);
                textDiceValue.setLayoutX(591);
                textBeforeValue.setText("Caiu: ");

                int a = Dice.roll();
                switch(a) {
                    case 1:
                        changeDice(1);
                        changeTile(1, player);
                        break;
                    case 2:
                        changeDice(2);
                        changeTile(2, player);
                        break;
                    case 3:
                        changeDice(3);
                        changeTile(3, player);
                        break;
                    case 4:
                        changeDice(4);
                        changeTile(4, player);
                        break;
                    case 5:
                        changeDice(5);
                        changeTile(5, player);
                        break;
                    case 6:
                        changeDice(6);
                        changeTile(6, player);
                        break;
                    default:
                        changeDice(0);
                }
                if(isPlayerOneTurn) {
                    isPlayerOneTurn = false;
                    try {
                        dos.writeInt(a);
                        dos.flush();
                    } catch(IOException e1) {
                        e1.printStackTrace();
                    }
                    timeline.play();
                } else {
                    try {
                        dos.writeInt(7);
                        dos.flush();
                    } catch(IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Executa o loop principal do jogo/servidor.
     * Verifica continuamente se o oponente não saiu do jogo, executa a jogada do oponente, e aguarda por requisições do servidor.
     */
    @Override
    public void run() {
        // Loop principal do jogo/servidor
        while (true) {
            // Verifica se o oponente não saiu do jogo e executa a jogada do oponente
            if (!playerLeft) {
                opponentMove();
            }
            // Se não for a vez do inimigo e não estiver aceito, escuta a requisição do servidor
            if (!isPlayerTwoTurn && !isAccepted) {
                serverRequest();
            }
            // Adiciona uma pequena pausa para evitar uso excessivo da CPU
            try {
                Thread.sleep(10); // pausa de 10 milissegundos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // restaura o estado interrompido
                break; // sai do loop se a thread for interrompida
            }
        }
    }
}

