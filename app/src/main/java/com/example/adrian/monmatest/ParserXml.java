package com.example.adrian.monmatest;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ParserXml {

    // Namespace general. null si no existe
    private static final String ns = null;

    // Constantes del archivo Xml
    private static final String ETIQUETA_PREGUNTAS = "hoteles";

    private static final String ETIQUETA_PREGUNTA = "hotel";

    private static final String ETIQUETA_ID_PREGUNTA = "idPregunta";
    public static final String ETIQUETA_ENUNCIADO = "enunciado";
    private static final String ETIQUETA_CATEGORIA = "categoria";
    private static final String ETIQUETA_RSP1 = "rsp1";
    private static final String ETIQUETA_RSP2 = "rsp2";
    private static final String ETIQUETA_RSP3 = "rsp3";
    private static final String ETIQUETA_RSP4 = "rsp4";
    private static final String ETIQUETA_URL_IMAGEN = "urlImagen";

    /**
     * Parsea un flujo XML a una lista de objetos {@link Pregunta}
     *
     * @param in flujo
     * @return Lista de hoteles
     * @throws XmlPullParserException
     * @throws IOException
     */
    public List<Pregunta> parsear(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
            parser.setInput(in, null);
            parser.nextTag();
            return leerPreguntas(parser);
        } finally {
            in.close();
        }
    }

    /**
     * Convierte una serie de etiquetas <hotel> en una lista
     *
     * @param parser
     * @return lista de hoteles
     * @throws XmlPullParserException
     * @throws IOException
     */
    private List<Pregunta> leerPreguntas(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        List<Pregunta> listaHoteles = new ArrayList<Pregunta>();

        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PREGUNTAS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String nombreEtiqueta = parser.getName();
            // Buscar etiqueta <hotel>
            if (nombreEtiqueta.equals(ETIQUETA_PREGUNTA)) {
                listaHoteles.add(leerPregunta(parser));
            } else {
                saltarEtiqueta(parser);
            }
        }
        return listaHoteles;
    }

    /**
     * Convierte una etiqueta <hotel> en un objero Hotel
     *
     * @param parser parser XML
     * @return nuevo objeto Hotel
     * @throws XmlPullParserException
     * @throws IOException
     */
    private Pregunta leerPregunta(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_PREGUNTA);
        int idPregunta = 0;
        String enunciado = null;
        String categoria = null;
        String rsp1 = null;
        String rsp2 = null;
        String rsp3 = null;
        String rsp4 = null;
        String urlImagen = null;
        String descripcion = null;
        HashMap<String, String> valoracion = new HashMap<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();

            switch (name) {
                case ETIQUETA_ID_PREGUNTA:
                    idPregunta = leerIdPregunta(parser);
                    break;
                case ETIQUETA_ENUNCIADO:
                    enunciado = leerEnunciado(parser);
                    break;
                case ETIQUETA_CATEGORIA:
                    categoria = leerCategoria(parser);
                    break;
                case ETIQUETA_RSP1:
                    rsp1 = leerRsp1(parser);
                    break;
                case ETIQUETA_RSP2:
                    rsp2 = leerRsp2(parser);
                    break;
                case ETIQUETA_RSP3:
                    rsp3 = leerRsp3(parser);
                    break;
                case ETIQUETA_RSP4:
                    rsp4 = leerRsp4(parser);
                    break;
                case ETIQUETA_URL_IMAGEN:
                    urlImagen = leerUrlImagen(parser);
                    break;
                default:
                    saltarEtiqueta(parser);
                    break;
            }
        }
        return new Pregunta(idPregunta,
                enunciado,
                categoria,
                rsp1,
                rsp2,
                rsp3,
                rsp4,
                urlImagen);
    }

    // Procesa la etiqueta <idHotel> de los hoteles
    private int leerIdPregunta(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_ID_PREGUNTA);
        int idHotel = Integer.parseInt(obtenerTexto(parser));
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_ID_PREGUNTA);
        return idHotel;
    }

    // Procesa las etiqueta <nombre> de los hoteles
    private String leerEnunciado(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_ENUNCIADO);
        String nombre = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_ENUNCIADO);
        return nombre;
    }

    // Procesa la etiqueta <precio> de los hoteles
    private String leerCategoria(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_CATEGORIA);
        String precio = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_CATEGORIA);
        return precio;
    }

    // Procesa la etiqueta <valoracion> de los hoteles
        /*private HashMap<String, String> leerValoracion(XmlPullParser parser)
                throws IOException, XmlPullParserException {
            parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RSP1);
            String calificacion = parser.getAttributeValue(null, ATRIBUTO_CALIFICACION);
            String noOpiniones = parser.getAttributeValue(null, ATRIBUTO_OPINIONES);
            parser.nextTag();
            parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RSP1);

            HashMap<String, String> atributos = new HashMap<>();
            atributos.put(ATRIBUTO_CALIFICACION, calificacion);
            atributos.put(ATRIBUTO_OPINIONES, noOpiniones);

            return atributos;
        }*/

    // Procesa las etiqueta <urlImagen> de los hoteles
    private String leerUrlImagen(XmlPullParser parser) throws IOException, XmlPullParserException {
        String urlImagen;
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_URL_IMAGEN);
        urlImagen = obtenerTexto(parser);
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_URL_IMAGEN);
        return urlImagen;
    }

    // Procesa las etiqueta <descripcion> de los hoteles
    private String leerRsp1(XmlPullParser parser) throws IOException, XmlPullParserException {
        String descripcion = "";
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RSP1);
        String rsp1 = parser.getPrefix();
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RSP1);
        return descripcion;
    }

    // Procesa las etiqueta <descripcion> de los hoteles
    private String leerRsp2(XmlPullParser parser) throws IOException, XmlPullParserException {
        String descripcion = "";
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RSP2);
        String rsp2 = parser.getPrefix();
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RSP2);
        return descripcion;
    }

    // Procesa las etiqueta <descripcion> de los hoteles
    private String leerRsp3(XmlPullParser parser) throws IOException, XmlPullParserException {
        String descripcion = "";
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RSP3);
        String rsp3 = parser.getPrefix();
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RSP3);
        return descripcion;
    }

    // Procesa las etiqueta <descripcion> de los hoteles
    private String leerRsp4(XmlPullParser parser) throws IOException, XmlPullParserException {
        String descripcion = "";
        parser.require(XmlPullParser.START_TAG, ns, ETIQUETA_RSP4);
        String rsp4 = parser.getPrefix();
        parser.require(XmlPullParser.END_TAG, ns, ETIQUETA_RSP4);
        return descripcion;
    }

    // Obtiene el texto de los atributos
    private String obtenerTexto(XmlPullParser parser) throws IOException, XmlPullParserException {
        String resultado = "";
        if (parser.next() == XmlPullParser.TEXT) {
            resultado = parser.getText();
            parser.nextTag();
        }
        return resultado;
    }

    // Salta aquellos objeteos que no interesen en la jerarquía XML.
    private void saltarEtiqueta(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}

