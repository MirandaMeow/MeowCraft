package cn.miranda.MeowCraft.Utils;

import org.bukkit.Note;

public class NoteWithTime {
    private final Note note;
    private final int time;

    public NoteWithTime(Note note, int time) {
        this.note = note;
        this.time = time;
    }

    public Note getNote() {
        return this.note;
    }

    public int getTime() {
        return this.time;
    }
}
