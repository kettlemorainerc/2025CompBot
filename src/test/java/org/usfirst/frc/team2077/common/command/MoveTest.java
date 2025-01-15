package org.usfirst.frc.team2077.common.command;

import edu.wpi.first.wpilibj2.command.Subsystem;
import org.junit.jupiter.api.*;
import org.usfirst.frc.team2077.common.WheelPosition;
import org.usfirst.frc.team2077.common.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.usfirst.frc.team2077.common.WheelPosition.*;

class MoveTest {
    private MoveTestHardware hardware;
    private Move move;

    MoveTest() {
        this.hardware = new MoveTestHardware();
    }
    
    @BeforeEach void before() {
        clock = 0;
        hardware.chassis.getActualPosition().set(0, 0, 0);
        hardware.chassis.getActualPositionMeasured().set(0, 0, 0);
        
        move = null;
    }

    @Test void bot_goes_north() {
        useMove(new Move(hardware, 10, 0, 0));
        tick(0, 0, 0);
        tick(2, 0, 0);
        tick(4, 0, 0);
        tick(6, 0, 0);
        tick(8, 0, 0);
        tick(10, 0, 0);
        assertTrue(move.isFinished());
        tick(10, 0, 0);
        assertTrue(move.isFinished());
    }

    @Test void bot_goes_east() {
        useMove(new Move(hardware, 0, 10, 0));
        tick(0, 0, 0);
        tick(0, 2, 0);
        tick(0, 4, 0);
        tick(0, 6, 0);
        tick(0, 8, 0);
        tick(0, 10, 0);
        assertTrue(move.isFinished());
        tick(0, 10, 0);
        assertTrue(move.isFinished());
    }
    
    @Test public void bot_goes_north_and_east() {
        useMove(new Move(hardware, 10, 10, 0));
        tick(0, 0, 0);
        tick(2, 2, 0);
        tick(4, 4, 0);
        tick(6, 6, 0);
        tick(8, 8, 0);
        tick(10, 10, 0);
        assertTrue(move.isFinished());
        tick(10, 10, 0);
        assertTrue(move.isFinished());
    }

    @Test public void bot_rotates() {
        useMove(new Move(hardware, 50));

        tick(0, 0, 0);
        tick(0, 0, 10);
        tick(0, 0, 20);
        tick(0, 0, 30);
        tick(0, 0, 40);
        tick(0, 0, 50);
        assertTrue(move.isFinished());
        tick(0, 0, 50);
        assertTrue(move.isFinished());
    }

    @Test public void bot_does_everything() {
        useMove(new Move(hardware, 10, 10, 50));

        // I'm not sure what's expected utilizing this constructor.
        // So these are just outputs from running it

        // New velocity: [10.0, 10.0, 50.0]
        // N:0.0in E:0.0in A:0.0deg | done: false
        tick(0, 0, 0);
        // New velocity: [10.0, 10.0, 50.0]
        // N:1.8in E:2.2in A:10.0deg | done: false
        tick(1.8, 2.2, 10);
        // New velocity: [10.0, 10.0, 50.0]
        // N:3.2in E:4.6in A:20.0deg | done: false
        tick(3.2, 4.6, 20);
        // New velocity: [10.0, 10.0, 50.0]
        // N:4.2in E:7.3in A:30.0deg | done: false
        tick(4.2, 7.3, 30);
        // New velocity: [10.0, 10.0, 50.0]
        // N:4.7in E:10.0in A:40.0deg | done: false
        tick(4.7, 10, 40);
        // New velocity: [10.0, 10.0, 50.0]
        // N:4.7in E:12.9in A:50.0deg | done: false
        tick(4.7, 12.9, 50);
        // New velocity: [10.0, 10.0, 0.0]
        // N:4.2in E:15.7in A:60.0deg | done: true
        tick(4.2, 15.7, 60);
        assertTrue(move.isFinished());
    }

    private void useMove(Move move) {
        this.move = move;
        this.move.initialize();
    }

    private void tick(double positionN, double positionE, double positionR) {
        tick(positionN, positionE, positionR, true);
    }

    private void tick() {
        tick(0, 0, 0, false);
    }

    private void tick(double positionN, double positionE, double positionR, boolean test) {
        var chassis = hardware.getChassis();

        chassis.periodic();
        move.execute();
        boolean done = move.isFinished();
        if(done) move.end(false);

        var position = chassis.getPosition();

        if(test) {
            assertArrayEquals(
                  new double[] {positionN, positionE, positionR},
                  position.get(),
                  0.1
            );
        }
    }


    public static final double CHANGE_IN_TIME = 0.2;
    private static double clock = 0;
    private static double getNext() {
        clock += CHANGE_IN_TIME;
        return clock;
    }

    private static class MoveTestHardware extends HardwareRequirements<TestDriveModule, TestChassis> {
        private final Map<WheelPosition, TestDriveModule> modules;
        private final TestChassis chassis;
        
        MoveTestHardware() {
            super(new Subsystem() {}, new Subsystem() {}, null, null);

            this.modules = Map.of(
                  FRONT_LEFT, makeWheel(FRONT_LEFT),
                  FRONT_RIGHT, makeWheel(FRONT_RIGHT),
                  BACK_LEFT, makeWheel(BACK_LEFT),
                  BACK_RIGHT, makeWheel(BACK_RIGHT)
            );

            this.chassis = new TestChassis(this, MoveTest::getNext);
        }

        @Override public TestChassis getChassis() {
            return chassis;
        }

        @Override public TestDriveModule getWheel(WheelPosition pos) {
            return modules.get(pos);
        }
        
        private static TestDriveModule makeWheel(WheelPosition pos) {
            return new TestDriveModule(pos, TestChassis.MAX_WHEEL_VELOCITY);
        }
    }
}