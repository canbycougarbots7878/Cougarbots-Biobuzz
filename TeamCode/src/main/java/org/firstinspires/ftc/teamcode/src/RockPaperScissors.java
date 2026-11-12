package org.firstinspires.ftc.teamcode.src;
import java.util.ArrayList;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import java.util.Random;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
@SuppressWarnings("unused")
@TeleOp(name="rock, paper, scissors (don't use at competitions", group="Basic")
public class RockPaperScissors extends LinearOpMode {

    String player1 = "paper";
    String player2 = "scissors";
    int player1_wins = 0;
    int player2_wins = 0;
    int Ties = 0;
    ArrayList<String> randombuttons1 = new ArrayList<>();

    ArrayList<String> randombuttons2 = new ArrayList<>();

    @Override
    public void runOpMode() throws InterruptedException {
        //adding strings to list for gun
        randombuttons1.add("gamepad1.x");
        randombuttons1.add("gamepad1.y");
        randombuttons1.add("gamepad1.b");
        randombuttons1.add("gamepad1.dpaddown");
        randombuttons1.add("gamepad1.back");
        randombuttons1.add("gamepad1.leftbummper");
        randombuttons1.add("gamepad1.rightbummper");
        randombuttons1.add("gamepad1.lefttrigger");
        randombuttons1.add("gamepad1.righttrigger");
        randombuttons1.add("gamepad1.leftstickbutton");
        randombuttons1.add("gamepad1.rightstickbutton");

        randombuttons2.add("gamepad2.x");
        randombuttons2.add("gamepad2.y");
        randombuttons2.add("gamepad2.b");
        randombuttons2.add("gamepad2.dpaddown");
        randombuttons2.add("gamepad2.back");
        randombuttons2.add("gamepad2.leftbummper");
        randombuttons2.add("gamepad2.rightbummper");
        randombuttons2.add("gamepad2.lefttrigger");
        randombuttons2.add("gamepad2.righttrigger");
        randombuttons2.add("gamepad2.leftstickbutton");
        randombuttons2.add("gamepad2.rightstickbutton");

        Random rand = new Random();//picking a random button for each gamepad
        String random1 = randombuttons1.get(rand.nextInt(randombuttons1.size()));
        String random2 = randombuttons2.get(rand.nextInt(randombuttons2.size()));

        waitForStart();
        while (opModeIsActive()) {//which each player chose
            if (gamepad1.dpadUpWasPressed()){
                player1 = "rock";
            } else if (gamepad1.dpadLeftWasPressed()) {
                player1 = "paper";
            } else if (gamepad1.dpadRightWasPressed()) {
                player1 = "scissors";
            }
            if (gamepad2.dpadUpWasPressed()){
                player2 = "rock";
            } else if (gamepad2.dpadLeftWasPressed()) {
                player2 = "paper";
            } else if (gamepad2.dpadRightWasPressed()) {
                player2 = "scissors";
            }

            if (gamepad1.aWasPressed()){//it doesn't see who won until A on player 1 was pressed
                TimeUnit.SECONDS.sleep(1);//waits so it isn't spammed

                if (Objects.equals(player1, player2)){//who won
                    telemetry.addLine("Tie");
                    Ties += 1;
                } else if (Objects.equals(player1, "rock") && Objects.equals(player2, "scissors")) {
                 telemetry.addLine("Player1 Wins!!");
                 player1_wins += 1;
                }else if (Objects.equals(player1, "paper") && Objects.equals(player2, "rock")){
                    telemetry.addLine("player1 Wins!!!");
                    player1_wins += 1;
                } else if (Objects.equals(player1, "scissors") && Objects.equals(player2, "paper")) {
                    telemetry.addLine("Player1 Wins!!");
                    player1_wins += 1;
                }else {
                    telemetry.addLine("player2 Wins!!");
                    player2_wins += 1;
                }

                if (random1.equals("gamepad1.x") && gamepad1.xWasPressed()){//random gun insta-win code
                    telemetry.addLine("Player1 Wins!!");
                    player1_wins += 1;
                    player1 = "Gun";
                }
                if (random1.equals("gamepad1.y") && gamepad1.yWasPressed()){
                    telemetry.addLine("Player1 Wins!!");
                    player1_wins += 1;
                    player1 = "Gun";
                }
                if (random1.equals("gamepad1.b") && gamepad1.bWasPressed()){
                    telemetry.addLine("Player1 Wins!!");
                    player1_wins += 1;
                    player1 = "Gun";
                }
                if (random1.equals("gamepad1.dpaddown") && gamepad1.dpadRightWasPressed()){
                    telemetry.addLine("Player1 Wins!!");
                    player1_wins += 1;
                    player1 = "Gun";
                }

                //prints witch each player chose and how many wins each player has as well as ties
                telemetry.addData("player1", player1);
                telemetry.addData("player2", player2);
                telemetry.addData("player1 Wins", player1_wins);
                telemetry.addData("player2 Wins", player2_wins);
                telemetry.addData("Ties", Ties);

            }

            telemetry.update();
        }
    }
}
