package cn.miranda.MeowCraft.Utils;

public class Note {
    private int realPitch;
    private final int pitch;
    private final String tonality;
    private final int sharp;

    public Note(int pitch, String tonality, int sharp) {
        this.pitch = pitch;
        this.tonality = tonality;
        this.sharp = sharp;
        this.realPitch = getRealPitch();
    }

    public org.bukkit.Note getNote(int offset) {
        this.realPitch += offset;
        if (this.realPitch < 0 || this.realPitch > 24) {
            return null;
        }
        return new org.bukkit.Note(this.realPitch);
    }

    private int getRealPitch() {
        int tune = 0;
        switch (this.tonality) {
            case "C":
                tune = 6;
                break;
            case "D":
                tune = 8;
                break;
            case "E":
                tune = 10;
                break;
            case "F":
                tune = 11;
                break;
            case "G":
                tune = 13;
                break;
            case "A":
                tune = 15;
                break;
            case "B":
                tune = 17;
                break;
            default:
                break;
        }
        int scale;
        switch (this.pitch) {
            case 1:
                scale = 0;
                break;
            case 2:
                scale = 2;
                break;
            case 3:
                scale = 4;
                break;
            case 4:
                scale = 5;
                break;
            case 5:
                scale = 7;
                break;
            case 6:
                scale = 9;
                break;
            case 7:
                scale = 11;
                break;
            default:
                scale = -1;
        }
        tune += scale;
        switch (this.sharp) {
            case -1:
                tune -= 12;
                break;
            case 1:
                tune += 12;
            case 0:
            default:
                break;
        }
        return tune;
    }
}
