package org.firstinspires.ftc.teamcode.src;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.Objects;

@SuppressWarnings("unused")
@TeleOp(name="Rock Paper Scissors (Don't use at competitions)", group="Basic")
public class Test extends LinearOpMode {

    String player1 = "paper";
    int player1_wins = 0;
    int player2_wins = 0;
    int Ties = 0;
    String player2 = "scissors";
    ArrayList<String> randombuttons = new ArrayList<String>();

    @Override
    public void runOpMode() throws InterruptedException {
        randombuttons.add("gamepad1");
        waitForStart();
        while (opModeIsActive()) {
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
            if (gamepad1.aWasPressed()) {
                TimeUnit.SECONDS.sleep(1);
                if (Objects.equals(player1, player2)) {
                    telemetry.addLine("Tie");
                    Ties += 1;
                }else if (Objects.equals(player1, "rock") && Objects.equals(player2, "scissors")) {
                    telemetry.addLine("player1 wins!!");
                    player1_wins += 1;
                }else if (Objects.equals(player1, "paper") && Objects.equals(player2, "rock")) {
                    telemetry.addLine("player1 wins!!");
                    player1_wins += 1;
                }else if (Objects.equals(player1, "scissors") && Objects.equals(player2, "paper")){
                    telemetry.addLine("player1 wins!!!");
                    player1_wins += 1;
                }else {
                    telemetry.addLine("player2 wins!!");
                    player2_wins += 1;
                }

                telemetry.addData("player1", player1);
                telemetry.addData("player2", player2);
                telemetry.addData("player1 wins", player1_wins);
                telemetry.addData("player2 wins", player2_wins);
                telemetry.addData("Ties", Ties);


            }

            telemetry.update();
        }
    }
}
