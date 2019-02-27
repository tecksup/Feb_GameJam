package com.thecubecast.ReEngine.worldObjects.AI;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;

public enum EnemyState implements State<Smart> {

    IDLE() {
        @Override
        public void enter(Smart Student) { // INIT

        }

        @Override
        public void update(Smart Student) {
            //Check for changes, then update state

        }

        @Override
        public void exit(Smart Student) {

        }

        @Override
        public boolean onMessage(Smart Student, Telegram telegram) {
            return false;
        }
    },

    WANDER() {
        @Override
        public void enter(Smart Student) { // INIT

        }

        @Override
        public void update(Smart Student) {
            //Check for changes, then update state
            //Student.WorldObject.setPosition(Student.WorldObject.getPosition().x + 1, Student.WorldObject.getPosition().y);

        }

        @Override
        public void exit(Smart Student) {

        }

        @Override
        public boolean onMessage(Smart Student, Telegram telegram) {
            return false;
        }
    },

    HUNTING() {

        float Speed = 16f;

        @Override
        public void enter(Smart Student) { // INIT
            Student.updatePath(true);
            Student.setDestination(Student.WorldObject.player.getPosition());
        }

        @Override
        public void update(Smart Student) {

            //Check for changes, then update state
            if (Student.getPath().nodes.size > 1) {

                Vector2 temp = new Vector2(Student.WorldObject.getPosition().x, Student.WorldObject.getPosition().y);
                temp.interpolate(new Vector2(Student.getPath().get(1).x * 16, Student.getPath().get(1).y * 16), 0.15f, Interpolation.linear);

                Student.WorldObject.setPosition(temp.x, temp.y, 0);
                if (Math.abs(Student.getPath().get(1).x * 16 - Student.WorldObject.getPosition().x) < 1) {
                    if (Math.abs(Student.getPath().get(1).y * 16 - Student.WorldObject.getPosition().y) < 1) {
                        Student.WorldObject.setPosition(Student.getPath().get(1).x * 16, Student.getPath().get(1).y * 16, 0);
                        Student.updatePath(true);
                    }
                }
            } else {
                Student.getStateMachine().changeState(IDLE);
            }
        }

        @Override
        public void exit(Smart Student) { // Leave this state

        }

        @Override
        public boolean onMessage(Smart Student, Telegram telegram) {
            return false;
        }
    },

    ATTACKING() {
        @Override
        public void enter(Smart Student) { // INIT
            Student.updatePath(true);
        }

        @Override
        public void update(Smart Student) {
            //Check for changes, then update state
            //if (Student.WorldObject.player)
        }

        @Override
        public void exit(Smart Student) { // Leave this state

        }

        @Override
        public boolean onMessage(Smart Student, Telegram telegram) {
            return false;
        }
    }

}
