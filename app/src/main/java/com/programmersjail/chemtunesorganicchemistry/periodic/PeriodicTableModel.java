package com.programmersjail.chemtunesorganicchemistry.periodic;

public class PeriodicTableModel {

    private String name,symbol,group_name,period,block,atomic_number,state_at_20_c,electron_config,
            melting_point,boiling_point,density_g_cm_q,relative_atomic_mass,key_isotopes,discover_date,
            discover_by,origin_of_the_name,allotropes,img;

    public PeriodicTableModel(String name, String symbol, String group_name, String period, String block,
                              String atomic_number, String state_at_20_c, String electron_config, String melting_point, String boiling_point,
                              String density_g_cm_q, String relative_atomic_mass, String key_isotopes, String discover_date,
                              String discover_by, String origin_of_the_name, String allotropes, String img) {
        this.name = name;
        this.symbol = symbol;
        this.group_name = group_name;
        this.period = period;
        this.block = block;
        this.atomic_number = atomic_number;
        this.state_at_20_c = state_at_20_c;
        this.electron_config = electron_config;
        this.melting_point = melting_point;
        this.boiling_point = boiling_point;
        this.density_g_cm_q = density_g_cm_q;
        this.relative_atomic_mass = relative_atomic_mass;
        this.key_isotopes = key_isotopes;
        this.discover_date = discover_date;
        this.discover_by = discover_by;
        this.origin_of_the_name = origin_of_the_name;
        this.allotropes = allotropes;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getGroup_name() {
        return group_name;
    }

    public String getPeriod() {
        return period;
    }

    public String getBlock() {
        return block;
    }

    public String getAtomic_number() {
        return atomic_number;
    }

    public String getState_at_20_c() {
        return state_at_20_c;
    }

    public String getElectron_config() {
        return electron_config;
    }

    public String getMelting_point() {
        return melting_point;
    }

    public String getBoiling_point() {
        return boiling_point;
    }

    public String getDensity_g_cm_q() {
        return density_g_cm_q;
    }

    public String getRelative_atomic_mass() {
        return relative_atomic_mass;
    }

    public String getKey_isotopes() {
        return key_isotopes;
    }

    public String getDiscover_date() {
        return discover_date;
    }

    public String getDiscover_by() {
        return discover_by;
    }

    public String getOrigin_of_the_name() {
        return origin_of_the_name;
    }

    public String getAllotropes() {
        return allotropes;
    }

    public String getImg() {
        return img;
    }
}
