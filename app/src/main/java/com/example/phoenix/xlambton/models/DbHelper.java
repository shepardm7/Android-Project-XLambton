package com.example.phoenix.xlambton.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Phoenix on 16-Aug-17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context) {
        super(context, "AgentDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Agents (id INTEGER PRIMARY KEY, name TEXT, photoPath TEXT, level TEXT, agency TEXT, website TEXT, country TEXT, phone TEXT, address TEXT)";
        String sql1 = "CREATE TABLE MissionHistory (id INTEGER PRIMARY KEY, name TEXT, date TEXT, status TEXT, agent_id INTEGER)";
        db.execSQL(sql);
        db.execSQL(sql1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS Agents";
        String sql1 = "DROP TABLE IF EXISTS MissionHistory";
        db.execSQL(sql);
        db.execSQL(sql1);
        onCreate(db);
    }

    private ContentValues getContentValues(Agent agent) {
        ContentValues data = new ContentValues();
        data.put("name", agent.getName());
        data.put("photoPath", agent.getPhotoPath());
        data.put("level", agent.getLevel());
        data.put("agency", agent.getAgency());
        data.put("website", agent.getWebsite());
        data.put("country", agent.getCountry());
        data.put("phone", agent.getPhone());
        data.put("address", agent.getAddress());
        return data;
    }
    public boolean addAgent(Agent agent) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE phone = '" + agent.getPhone() + "'", null);
        if (c.getCount() > 0)
            return false;

        ContentValues data = getContentValues(agent);
        db.insert("Agents", null, data);
        return true;
    }

    public void addMission(String name, String date, String status, long agentId) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("name", name);
        data.put("date", date);
        data.put("status", status);
        data.put("agent_id", agentId);
        db.insert("MissionHistory", null, data);
    }

    public ArrayList<String>[] getMissions(long agentId) {
        ArrayList<String>[] missions = new ArrayList[3];
        ArrayList<String> missionNames = new ArrayList<>();
        ArrayList<String> missionDates = new ArrayList<>();
        ArrayList<String> missionStatus = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM MissionHistory WHERE agent_id = " + agentId, null);
        while (c.moveToNext()) {
            missionNames.add(c.getString(c.getColumnIndex("name")));
            missionDates.add(c.getString(c.getColumnIndex("date")));
            missionStatus.add(c.getString(c.getColumnIndex("status")));
        }
        c.close();
        missions[0] = missionNames;
        missions[1] = missionDates;
        missions[2] = missionStatus;
        return missions;
    }

    public ArrayList<Agent> getAllAgents() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents", null);
        ArrayList<Agent> agents = new ArrayList<>();

        while (c.moveToNext()) {
            Agent agent = new Agent();
            agent.setId(c.getLong(c.getColumnIndex("id")));
            agent.setName(c.getString(c.getColumnIndex("name")));
            agent.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));
            agent.setLevel(c.getString(c.getColumnIndex("level")));
            agent.setAgency(c.getString(c.getColumnIndex("agency")));
            agent.setWebsite(c.getString(c.getColumnIndex("website")));
            agent.setCountry(c.getString(c.getColumnIndex("country")));
            agent.setPhone(c.getString(c.getColumnIndex("phone")));
            agent.setAddress(c.getString(c.getColumnIndex("address")));
            agents.add(agent);
        }
        c.close();
        return agents;
    }

    public ArrayList<Agent> getSearchedAgents(String agentName) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE name LIKE '%" + agentName + "%'", null);
        ArrayList<Agent> agents = new ArrayList<>();

        while (c.moveToNext()) {
            Agent agent = new Agent();
            agent.setId(c.getLong(c.getColumnIndex("id")));
            agent.setName(c.getString(c.getColumnIndex("name")));
            agent.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));
            agent.setLevel(c.getString(c.getColumnIndex("level")));
            agent.setAgency(c.getString(c.getColumnIndex("agency")));
            agent.setWebsite(c.getString(c.getColumnIndex("website")));
            agent.setCountry(c.getString(c.getColumnIndex("country")));
            agent.setPhone(c.getString(c.getColumnIndex("phone")));
            agent.setAddress(c.getString(c.getColumnIndex("address")));
            agents.add(agent);
        }
        c.close();
        return agents;
    }

    public void dbDeleteAgent(Agent agent) {
        SQLiteDatabase db = getWritableDatabase();
        String[] param = {String.valueOf(agent.getId())};
        String sql = "DELETE FROM MissionHistory WHERE agent_id = " + agent.getId();
        db.execSQL(sql);
        db.delete("Agents", "id = ?", param);
    }

    public boolean dbAlterAgent(Agent agent, long idOfEditingAgent) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE phone = '" + agent.getPhone() + "' AND id != " + idOfEditingAgent, null);
        if (c.getCount() > 0)
            return false;
        ContentValues data = getContentValues(agent);
        String[] param = {String.valueOf(agent.getId())};
        db.update("Agents", data, "id = " + idOfEditingAgent, null);
        return true;
    }

    public Agent dbGetAgentAt(long agentId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE ID = " + agentId, null);
        c.moveToFirst();
        Agent agent = new Agent();
        agent.setId(c.getLong(c.getColumnIndex("id")));
        agent.setName(c.getString(c.getColumnIndex("name")));
        agent.setPhotoPath(c.getString(c.getColumnIndex("photoPath")));
        agent.setLevel(c.getString(c.getColumnIndex("level")));
        agent.setAgency(c.getString(c.getColumnIndex("agency")));
        agent.setWebsite(c.getString(c.getColumnIndex("website")));
        agent.setCountry(c.getString(c.getColumnIndex("country")));
        agent.setPhone(c.getString(c.getColumnIndex("phone")));
        agent.setAddress(c.getString(c.getColumnIndex("address")));
        c.close();
        return agent;
    }

    public boolean isAgent(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE phone = ?", new String[]{phone});
        int result = c.getCount();
        c.close();
        return result > 0;
    }

    public String getAgentNameFromPhone(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Agents WHERE phone = ?", new String[]{phone});
        c.moveToFirst();
        String name = c.getString(c.getColumnIndex("name"));
        return name;
    }
}
