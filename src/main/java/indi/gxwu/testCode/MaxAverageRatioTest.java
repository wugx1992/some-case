package indi.gxwu.testCode;

import org.junit.Test;

/**
 * @author: gx.wu
 * @date: 2022/10/12
 * @description:
 * 一所学校里有一些班级，每个班级里有一些学生，现在每个班都会进行一场期末考试。给你一个二维数组 classes ，其中 classes[i] = [passi, totali] ，表示你提前知道了第 i 个班级总共有 totali 个学生，其中只有 passi 个学生可以通过考试。
 * 给你一个整数 extraStudents ，表示额外有 extraStudents 个聪明的学生，他们 一定 能通过任何班级的期末考。你需要给这 extraStudents 个学生每人都安排一个班级，使得 所有 班级的 平均 通过率 最大 。
 * 一个班级的 通过率 等于这个班级通过考试的学生人数除以这个班级的总人数。平均通过率 是所有班级的通过率之和除以班级数目。
 * 请你返回在安排这 extraStudents 个学生去对应班级后的 最大 平均通过率。与标准答案误差范围在 10-5 以内的结果都会视为正确结果。
 **/
public class MaxAverageRatioTest {


    @Test
    public void t(){
        int a = 2, b = 5;
        for(int i = 1; i< 100; i++) {
            double t1 = (double)a/b;
            a++;
            b++;
            double t2 = (double) a/b;
            System.out.println(t2+" "+ (t2-t1));
        }
    }

    @Test
    public void maxAverageRatioTest(){
        int[][] classes; int extraStudents;

        /**
         * 输入：classes = [[1,2],[3,5],[2,2]], extraStudents = 2
         * 输出：0.78333
         * 解释：你可以将额外的两个学生都安排到第一个班级，平均通过率为 (3/4 + 3/5 + 2/2) / 3 = 0.78333 。
         */
        classes = new int[][]{{1,2},{3,5},{2,2}};
        extraStudents = 2;
        assert maxAverageRatio(classes, extraStudents) >= 0.78333;

        classes = new int[][]{{2,4},{3,9},{4,5},{2,10}};
        extraStudents = 4;
        assert maxAverageRatio(classes, extraStudents) >= 0.53485;

        classes = new int[][]{{572,807},{80,628},{412,889},{325,482},{330,660},{180,581},{43,319},{289,824},{18,434},{554,568},{217,613},{142,798},{99,680},{549,744},{465,596},{270,780},{343,952},{102,107},{366,431},{654,747},{444,455},{38,395},{633,992},{875,998},{554,896},{8,348},{118,980},{195,398},{242,722},{301,988},{445,490},{369,698},{405,452},{447,467},{88,992},{604,810},{304,904},{299,493},{68,498},{159,690},{96,105},{696,999},{670,744},{544,865},{408,781},{183,793},{381,795},{188,963},{71,877},{956,973},{439,535},{18,942},{233,809},{446,790},{187,582},{533,833},{94,841},{51,470},{221,880},{337,377},{114,146},{239,648},{11,956},{170,496},{504,992},{208,290},{268,584},{942,949},{626,811},{266,578},{48,465},{308,336},{77,494},{456,898},{50,496},{374,477},{371,500},{19,353},{202,940},{708,833},{157,823},{74,931},{79,889},{320,872},{417,421},{271,322},{695,702},{529,683},{127,277},{231,396},{756,912},{86,486},{142,808},{184,575},{528,896},{362,555},{245,290},{95,774},{166,778},{377,452},{778,821},{222,453},{49,406},{266,297},{3,342},{189,672},{291,770},{623,800},{389,945},{209,297},{306,718},{366,777},{710,970},{504,888},{291,924},{683,905},{264,749},{455,975},{221,668},{218,794},{210,307},{39,392},{4,818},{571,656},{115,384},{355,397},{787,907},{231,688},{69,754},{36,235},{538,649},{569,656},{383,745},{424,801},{328,718},{339,999},{559,850},{60,305},{285,777},{263,465},{386,672},{579,610},{399,791},{36,572},{626,886},{807,996},{691,838},{285,903},{40,540},{700,795},{242,262},{61,899},{390,747},{223,697},{348,863},{286,465},{771,985},{391,830},{99,306},{558,945},{533,966},{699,776},{193,778},{169,809},{888,959},{531,940},{267,455},{17,775},{436,573},{33,344},{167,990},{55,327},{193,826},{783,962},{427,486},{235,489},{574,675},{669,909},{85,793},{358,731},{174,443},{603,734},{556,691},{359,908},{111,878},{71,339},{52,217},{125,137},{121,210},{337,571},{636,818},{144,478},{319,388},{12,870},{791,800},{150,468},{382,488},{480,561},{490,642},{687,926},{455,760},{858,979},{413,920},{737,971},{40,83},{563,572},{363,664},{134,335},{499,852},{3,423},{126,358},{63,978},{256,360},{210,699},{53,758},{392,658},{299,754},{540,756},{414,668},{700,972},{44,139},{48,891},{784,857},{216,876},{733,935},{231,608},{396,911},{634,760},{330,356},{461,997},{377,619},{39,829},{42,126},{86,890},{763,831},{71,608},{56,155},{28,983},{101,809},{11,984},{419,836},{790,914},{325,601},{250,684},{538,562},{241,936},{203,475},{463,636},{409,450},{14,56},{790,917},{308,804},{210,296},{251,707},{280,883},{327,888},{260,599},{577,871},{679,824},{724,777},{75,356},{270,552},{206,834},{163,425},{441,596},{150,579},{228,399},{12,333},{70,389},{446,705},{664,913},{122,594},{509,525},{243,492},{68,924},{179,773},{319,581},{681,885},{270,721},{321,584},{353,931},{700,735},{233,912},{571,986},{613,784},{394,965},{115,290},{234,713},{263,412},{559,877},{478,581},{828,843},{285,569},{197,212},{754,892},{181,985},{553,848},{200,242},{317,642},{806,957},{77,867},{67,767},{237,269},{216,595},{203,375},{155,870},{157,666},{400,990},{468,906},{65,539},{158,606},{200,216},{471,487},{146,350},{57,928},{41,827},{213,688},{519,879},{728,869},{154,214},{673,827},{607,778},{135,908},{622,809},{470,791},{836,852},{46,599},{323,467},{205,237},{90,399},{93,281},{24,364},{532,721},{166,336},{316,486},{266,482},{108,473},{902,914},{306,369},{222,542},{96,426},{738,881},{773,929},{862,873},{298,785},{233,773},{420,753},{287,780},{943,989},{113,222},{581,871},{212,284},{360,439},{10,972},{4,658},{327,489},{125,947},{382,729},{242,567},{77,736},{99,112},{35,423},{929,967},{835,996},{53,316},{857,868},{582,954},{54,634},{363,939},{188,342},{457,886},{9,334},{468,660},{38,717},{520,534},{660,668},{656,775},{57,414},{112,818},{433,669},{703,967},{527,941},{817,968},{102,678},{358,823},{393,441},{105,886},{149,308},{230,393},{389,877},{463,632},{659,687},{603,746},{670,924},{390,756},{487,560},{424,943},{170,872},{127,645},{697,761},{54,225},{63,125},{377,490},{173,752},{233,603},{665,672},{498,989},{608,913},{58,710},{427,777},{480,803},{381,809},{594,739},{582,922},{285,837},{179,728},{216,624},{510,932},{228,475},{352,879},{128,217},{184,790},{763,902},{180,998},{55,865},{832,960},{97,736},{448,528},{109,680},{195,791},{629,829},{414,612},{227,990},{80,415},{382,447},{450,458},{561,698},{431,929},{107,621},{822,903},{453,712},{12,940},{729,954},{557,673},{38,444},{61,803},{413,695},{58,870},{464,665},{349,434},{60,605},{456,640},{270,512},{704,941},{440,673},{499,747},{714,814},{173,982},{586,600},{94,131},{329,944},{693,960},{83,962},{246,408},{51,916},{255,349},{309,632},{218,677},{380,904},{453,746},{694,981},{195,517},{127,214},{114,280},{433,642},{430,874},{647,687},{312,947},{255,993},{129,827},{275,287},{242,910},{935,990},{16,121},{701,824},{47,795},{885,932},{343,684},{139,533},{98,637},{684,916},{739,913},{467,610},{330,595},{65,265},{611,897},{232,894},{305,959},{419,675},{309,879},{171,859},{192,839},{668,872},{256,298},{146,177},{230,979},{480,929},{489,685},{380,936},{679,862},{11,591},{349,405},{49,379},{246,763},{322,783},{25,488},{371,508},{79,532},{67,883},{834,960},{772,978},{147,888},{55,451},{93,952},{381,714},{270,804},{160,248},{261,529},{661,750},{372,408},{503,670},{184,887},{511,809},{340,551},{745,833},{204,426},{284,605},{410,452},{573,781},{7,90},{220,838},{369,624},{34,86},{188,618},{501,619},{399,507},{235,660},{434,507},{371,928},{453,760},{23,244},{530,773},{180,662},{177,874},{191,477},{235,586},{128,953},{49,413},{530,657},{42,720},{218,328},{136,926},{598,723},{205,904},{693,754},{145,582},{77,400},{152,772},{453,699},{267,977},{742,906},{383,496},{554,725},{255,571},{41,734},{186,814},{270,361},{564,836},{130,523},{822,959},{760,941},{330,990},{72,534},{149,508},{250,978},{221,241},{362,755},{236,615},{637,832},{338,712},{128,725},{332,740},{617,984},{137,533},{147,278},{358,449},{278,788},{411,938},{928,987},{45,471},{522,761},{269,446},{329,638},{36,686},{777,903},{655,816},{370,980},{308,954},{232,813},{50,755},{659,764},{49,529},{201,685},{662,956},{214,271},{107,926},{370,499},{592,798},{99,898},{17,574},{195,208},{359,834},{128,416},{218,243},{626,870},{187,815},{98,472},{239,255},{605,879},{4,931},{30,356},{42,384},{88,177},{484,806},{195,550},{37,300},{259,677},{22,477},{611,964},{234,897},{172,785},{525,946},{143,424},{353,949},{191,587},{797,808},{499,643},{339,460},{87,635},{780,971},{111,613},{590,789},{393,629},{54,359},{97,883},{222,1000},{462,854},{431,734},{443,676},{194,701},{62,870},{405,598},{148,237},{366,861},{59,670},{374,759},{338,887},{219,314},{34,951},{346,449},{197,306},{867,881},{8,496},{56,439},{57,379},{576,878},{873,953},{290,433},{290,907},{445,540},{283,702},{47,399},{216,641},{38,643},{213,748},{109,858},{102,746},{38,861},{322,767},{224,319},{418,747},{486,578},{63,966},{685,795},{348,975},{118,671},{72,815},{603,630},{8,783},{813,977},{709,899},{377,572},{361,698},{778,867},{40,129},{182,443},{115,748},{281,793},{721,979},{157,421},{1,202},{577,921},{430,437},{314,777},{337,863},{161,447},{554,792},{269,709},{637,717},{128,146},{46,49},{66,457},{530,713},{574,848},{872,892},{268,297},{636,710},{184,571},{248,489},{175,931},{1,721},{316,553},{114,264},{62,584},{185,748},{22,541},{366,813},{551,672},{65,615},{601,640},{607,636},{7,858},{367,534},{152,524},{28,529},{162,889},{390,437},{479,792},{10,911},{421,518},{82,94},{12,190},{824,843},{336,838},{395,528},{301,902},{169,729},{2,254},{15,463},{649,865},{198,840},{232,789},{516,699},{157,608},{40,893},{87,615},{214,294},{66,313},{103,847},{326,501},{65,1000},{35,622},{15,905},{571,808},{444,866},{11,182},{158,657},{557,561},{891,920},{126,927},{292,388},{148,173},{313,746},{162,410},{135,293},{331,540},{700,794},{403,769},{635,639},{282,496},{291,541},{660,891},{476,769},{317,794},{5,22},{808,830},{279,362},{810,855},{162,410},{417,418},{487,497},{52,527},{550,807},{246,253},{243,449},{62,998},{125,479},{600,839},{428,445},{565,664},{368,673},{275,685},{57,929},{362,550},{342,439},{389,658},{407,797},{13,590},{50,509},{142,658},{350,598},{320,852},{30,726},{401,971},{819,876},{81,424},{669,807},{535,944},{394,682},{71,764},{198,748},{78,342},{51,84},{535,565},{47,735},{302,766},{67,330},{700,886},{735,964},{272,825},{81,995},{124,656},{10,931},{192,273},{562,622},{826,853},{43,534},{61,536},{576,922},{168,342},{210,578},{24,258},{418,610},{426,980},{416,737},{427,873},{214,626},{170,808},{183,509},{90,202},{577,878},{231,516},{19,671},{573,892},{216,234},{870,915},{452,984},{599,682},{528,796},{161,946},{239,488},{513,574},{83,470},{179,763},{369,984},{344,473},{75,193},{476,594},{42,106},{689,925},{47,609},{587,796},{120,377},{137,303},{339,382},{170,253},{115,802},{93,352},{87,204},{158,690},{173,375},{843,903},{667,980},{119,247},{482,733},{466,774}};
        extraStudents = 60749;
        assert maxAverageRatio(classes, extraStudents) >= 0.573056;
    }

    public double maxAverageRatio2(int[][] classes, int extraStudents) {
        for(int i = 1; i<=extraStudents;i++){
            double maxDiff = Double.MIN_VALUE;
            int match = 0;
            for(int index = 0; index < classes.length; index++) {
                double diff = ((double) (classes[index][0]+1)/(classes[index][1]+1)) - ((double) classes[index][0]/classes[index][1]);
                if(diff >= maxDiff) {
                    match = index;
                    maxDiff = diff;
                }
            }
            classes[match][0] = classes[match][0]+1;
            classes[match][1] = classes[match][1]+1;
        }
        double result = 0;
        for(int[] cls : classes) {
            result += (double) cls[0]/cls[1];
        }
        result = result/ classes.length;
        return result;
    }


    public double maxAverageRatio(int[][] classes, int extraStudents) {
        makeMinHeap(classes, classes.length);
        for(int i = 1; i<=extraStudents;i++){
            classes[0][0] = classes[0][0]+1;
            classes[0][1] = classes[0][1]+1;
            minHeapFixDown(classes, classes.length, 0);
        }
        double result = 0;
        for(int[] cls : classes) {
            result += (double) cls[0]/cls[1];
        }
        result = result/ classes.length;
        return result;
    }



    /**
     * 建立最小堆
     * @param a
     * @param len
     */
    void makeMinHeap(int a[][], int len) {
        for (int root = len / 2 - 1; root >= 0; root--) {
            minHeapFixDown(a, len, root);
        }
    }

    /**
     * 小顶堆结点下沉操作
     * @param a
     * @param len
     * @param root
     */
    void minHeapFixDown(int a[][], int len, int root) {
        //i 的左节点：i*2+1，有节点：i*2+2，父节点：（i-1）/2
        int parent = root;
        int child = parent * 2 + 1;
        // 当下沉到叶子节点时，就不用调整了
        while (child < len) {
            if ((child + 1) < len && getIncreaseDiff(a[child]) < getIncreaseDiff(a[child + 1])) {
                child++;
            }
            if (getIncreaseDiff(a[child]) > getIncreaseDiff(a[parent])) {
                int temp[] = a[child];
                a[child] = a[parent];
                a[parent] = temp;
                parent = child;
                child = parent * 2 + 1;
            } else {
                break;
            }
        }
    }

    public double getIncreaseDiff(int[] cls){
        double t1 = (double) cls[0]/cls[1];
        double t2 = (double) (cls[0]+1)/(cls[1]+1);
        double diff = t2 - t1;
        return diff;
    }



}
