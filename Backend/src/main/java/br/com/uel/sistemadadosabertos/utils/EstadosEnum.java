package br.com.uel.sistemadadosabertos.utils;

public enum EstadosEnum {
    BRASIL("Brasil","BR","Brasil"),
    AMAZONAS("Amazonas", "AM" ,"Região Norte"),
    RORAIMA("Roraima", "RR" ,"Região Norte"),
    RONDONIA("Rondônia", "RO" ,"Região Norte"),
    PARÁ("Pará", "PA" ,"Região Norte"),
    TOCANTINS("Tocantins", "TO" ,"Região Norte"),
    AMAPÁ("Amapá", "AP" ,"Região Norte"),

    MARANHÃO("Maranhão", "MA", "Região Nordeste"),
    PIAUÍ("Piauí", "PI" ,"Região Nordeste"),
    CEARÁ("Ceará", "CE" ,"Região Nordeste"),
    RIO_GRANDE_NORTE("Rio Grande do Norte", "RN" ,"Região Nordeste"),
    PERNAMBUCO("Pernambuco", "PE" ,"Região Nordeste"),
    PARAÍBA("Paraíba", "PB" ,"Região Nordeste"),
    SERGIPE("Sergipe", "SE" ,"Região Nordeste"),
    ALAGOAS("Alagoas", "AL" ,"Região Nordeste"),
    BAHIA("Bahia", "BA" ,"Região Nordeste"),

    MATO_GROSSO("Mato Grosso","MT","Região Centro-Oeste"),
    MATO_GROSSO_SUL("Mato Grosso do Sul","MS","Região Centro-Oeste"),
    GOIÁS("Goiás","GO","Região Centro-Oeste"),
    DISTRITO_FEDERAL("Distrito Federal","DF","Região Centro-Oeste"),

    SÃO_PAULO("São Paulo","SP","Região Sudeste"),
    RIO_JANEIRO("Rio de Janeiro","RJ","Região Sudeste"),
    ESPÍRITO_SANTO("Espírito Santo","ES","Região Sudeste"),
    MINAS_GERAIS("Minas Gerais","MG","Região Sudeste"),

    PARANÁ("Paraná","PR","Região Sul"),
    RIO_GRANDE_SUL("Rio Grande do Sul","RS","Região Sul"),
    SANTA_CATARINA("Santa Catarina","SC","Região Sul");

    private String nome_est;
    private String sigla;
    private String regiao;

    private EstadosEnum(String nome_est, String sigla, String regiao){
        this.nome_est = nome_est;
        this.sigla = sigla;
        this.regiao = regiao;
    }

    public String getNome_Est() {
        return nome_est;
    }

    public String getSigla() {
        return sigla;
    }

    public String getRegiao() {
        return regiao;
    }

    public String getSiglaAndRegiao(){
        return this.sigla +"&" +this.regiao;
    }
}
