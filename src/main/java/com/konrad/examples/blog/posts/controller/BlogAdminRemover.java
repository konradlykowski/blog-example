package com.konrad.examples.blog.posts.controller;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class BlogAdminRemover implements RequestHandler<Map<String, String>, String> {
    private static Logger LOG = Logger.getLogger(BlogAdminRemover.class.getName());

    public BlogAdminRemover() {

    }

    public static void main(String args[]) {
        Map<String, String> input = new HashMap<String, String>() {{
            put("content", "{\"id\":\"marta-w-japonii.html\",\"title\":\"Marta w Porto\",\"category\":\"podroze\",\"date\":\"2017-10-15T09:00:00Z\", \"description\": \"Marta przyjechala do Porto zobaczyc co slychac\", \"content\":\"Japonia???? ???? ????? to niezwyk\u0142y kraj z bogatym dobytkiem kulturowym. Wszystko tam by\u0142o dla mnie niezwykle: ludzie, obyczaje, stroje, j\u0119zyk oraz jedzenie. Pomimo pozornego t\u0142oku na ulicach, dworcach oraz w metrze, ludzie s\u0105 \u201Cdziwnie\u201D spokojni. Nigdy dotychczas si\u0119 z czym\u015B takim nie spotka\u0142am. Pomy\u015Bla\u0142am, \u017Ce jako osoba zbzikowana na punkcie mody i kultury ubioru podziel\u0119 si\u0119 z Tob\u0105 moimi spostrze\u017Ceniami na ten temat z Japonii. <p\\/>\\r\\n<br\\/><span class=\\\\\\\"modal-title\\\\\\\">ELEGANCJA PO JAPO\u0143SKU<\\/span><br\\/>\\r\\n\u201CSpok\u00F3j\u201D tak\u017Ce panowa\u0142 w strojach japo\u0144czyk\u00F3w. Dzieci ju\u017C od najm\u0142odszych lat przyzwyczajane s\u0105 do ubioru zgodnego z og\u00F3lnie przyj\u0119tymi zasadami. W szko\u0142ach podstawowych zazwyczaj mundurki nie s\u0105 obowi\u0105zkowe, ale bardzo du\u017Co dzieci je nosi. W szko\u0142ach \u015Brednich to ju\u017C standard i obowi\u0105zek. <p\\/>Tradycyjny japo\u0144ski mundurek szkolny dla dziewcz\u0105t ma kr\u00F3j stroju marynarskiego i sk\u0142ada si\u0119 z bia\u0142ej bluzki, plisowanej sp\u00F3dniczki do kolan, trampek i kolan\u00F3wek. <p\\/>Ch\u0142opi\u0119ce mundurki s\u0105 szyte w stylu wojskowym; sk\u0142adaj\u0105 si\u0119 z bia\u0142ej koszuli z usztywnianym ko\u0142nierzykiem i ciemnych spodni. <p\\/>W Japonii dodatkowo obowi\u0105zuj\u0105 mundurki przeznaczone do noszenia na zaj\u0119cia wychowania fizycznego. Wszystko to wygl\u0105da bardzo estetycznie, nikt si\u0119 nie wyr\u00F3\u017Cnia i nie rzuca si\u0119 w oczy. <p\\/> Dziewczynki wracaj\u0105ce ze szko\u0142y wygl\u0105da\u0142y niesamowicie  w swoich mundurkach, a\u017C sama zat\u0119skni\u0142am za czasami szkolnymi i \u017Ca\u0142owa\u0142am, \u017Ce nie mia\u0142am okazji nosi\u0107 takiego prawdziwego mundurka. <br\\/> <br\\/>\\r\\nJapo\u0144ski str\u00F3j do pracy jest bardzo podobny do owych mundurk\u00F3w szkolnych, zawsze stonowany i elegancki. Kobiety nosz\u0105 bia\u0142e koszule, pastelowe bluzki, sp\u00F3dnice do kolan lub d\u0142ugie spodnie oraz czu\u0142enka na obcasie. Maj\u0105 dobre i cz\u0119sto markowe torebki, kt\u00F3re nie rzucaj\u0105 si\u0119 w oczy. Japonki pi\u0119kne l\u015Bni\u0105ce w\u0142osy i minimalny make up. Wszystko to tworzy bardzo przyjemny dla oka widok. Swoj\u0105 drog\u0105 Japonki bardzo dbaj\u0105 o sw\u00F3j wygl\u0105d, wida\u0107 to ju\u017C po ilo\u015Bci drogerii i kosmetyk\u00F3w w nich dost\u0119pnych. Raj!!! <p\\/>\\r\\nKobiety w Japonii po prostu kochaj\u0105 mod\u0119. M\u0142ode Japonki bywaj\u0105 bardziej alternatywne, w ich strojach mo\u017Cna odnale\u017A\u0107 wi\u0119cej kolor\u00F3w, nosz\u0105 kr\u00F3tkie sp\u00F3dniczki, wysokie buty i kolorowe w\u0142osy. <p\\/>\\r\\nM\u0119\u017Cczy\u017Ani natomiast wygl\u0105daj\u0105 bardzo klasycznie: bia\u0142a koszula, ciemne eleganckie spodnie oraz sk\u00F3rzana teczka. <br\\/>\\r\\nS\u0142ysza\u0142am kiedy\u015B takie japo\u0144skie przys\u0142owie:\\r\\n<i> \u201Cje\u015Bli gw\u00F3\u017Ad\u017A wystaje, trzeba go natychmiast wbi\u0107 na miejsce\u201D <\\/i>\\r\\nDok\u0142adnie tak samo jest w kwestii stroju, ka\u017Cdy jest taki sam i nikt nie \u201Cwystaje\u201D jak owy gw\u00F3\u017Ad\u017A. \\r\\n<br\\/><span class=\\\\\\\"modal-title\\\\\\\">KIMONO: KWINESENCJA JAPONII<\\/span><br\\/>\\r\\nWydaje mi si\u0119, \u017Ce prawdziwa kwintesencj\u0105 tradycji jest japo\u0144skie kimono, kt\u00F3re ma tak szerokie spektrum zastosowa\u0144 w zale\u017Cno\u015Bci od rodzaju uroczysto\u015Bci. Czy mo\u017Ce by\u0107 co\u015B bardziej japo\u0144skiego dla osoby zbzikowanej na punkcie mody ni\u017C tradycyjne kimono? <p\\/>\\r\\nKimono dla kobiet sk\u0142ada si\u0119 z dwunastu lub wi\u0119cej element\u00F3w, kt\u00F3re nale\u017Cy dobra\u0107, ubra\u0107 i umocowa\u0107 w specjalny spos\u00F3b. Z tego powodu w Japonii istniej\u0105 specjali\u015Bci, kt\u00F3rzy pomagaj\u0105 za\u0142o\u017Cy\u0107 odpowiednio te wszystkie elementy. <p\\/>\\r\\nKu mojemu zdziwieniu spotkali\u015Bmy wiele gejsz, na ulicach, w metrze i w restauracjach. Trudno by\u0142o oczy oderwa\u0107 od tak perfekcyjnie wygl\u0105daj\u0105cych kobiet. <p\\/>\\r\\nUda\u0142o mi si\u0119 zakupi\u0107 jeden rodzaj kimona, ale postanowi\u0142am nosi\u0107 je na sw\u00F3j w\u0142asny spos\u00F3b (KLIK- moja wariacja na temat kimona). <p\\/>\\r\\nW Japonii kimona nosz\u0105 te\u017C m\u0119\u017Cczy\u017Ani. Maj\u0105 one sta\u0142y kszta\u0142t i wykonane s\u0105 z tkanin o stonowanych barwach. Odmiennie ni\u017C w przypadku kimon damskich rodzaj i rang\u0119 uroczysto\u015Bci rozr\u00F3\u017Cnia si\u0119 poprzez dob\u00F3r odpowiednich dodatk\u00F3w. <br\\/> <br\\/>\\r\\nNa koniec ciekawostka. Kim s\u0105 owi tytu\u0142owi gajdzini? Gajdzin, a w\u0142a\u015Bciwie Gaijin to w j\u0119zyku japo\u0144skim \u201Ccudzoziemiec\u201D, \u201Cnie-Japo\u0144czyk\u201D lub \u201Cobcy\u201D, okre\u015Blenie to funkcjonuje w powszechnym u\u017Cytku. \\r\\n\",\"commentsCount\":\"12\",\"tags\": \"kokos kokos2\",\"image\":\"img/image1.JPGv\", \"location\": \"Zurich\"}");
            put("url", "https://search-iglapublic-b5vdg4dtkiwinlp7c7tm66juva.eu-central-1.es.amazonaws.com/posts/post/id");
        }};
        LOG.info(new BlogAdminRemover().handleRequest(input, null));
    }

    public String handleRequest(Map<String, String> input, Context context) {
        RestTemplate restTemplate = new RestTemplate();
        LOG.info(input.get("url"));
        LOG.info(input.get("content"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        restTemplate.delete(input.get("url"));
        return "SUCCEED";
    }


}