package org.lab6.localComands;

public class exit extends Command{
    @Override
    public void execute(){System.exit(0);}
    @Override
    public String getComment(){return "exit%выключает программу клиента";}

}
