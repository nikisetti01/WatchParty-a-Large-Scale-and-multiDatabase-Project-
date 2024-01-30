package com.example.LargeScaleProject.Model.httpRequest;

public enum Affinity {
        BadAffinity,
        GoodAffinity,
        SuperAffinity;

        public static Affinity calculateAffinity(int valore) {
            if (valore == 2) {
                return SuperAffinity;
            } else if (valore >= 2 && valore <= 5) {
                return GoodAffinity;
            } else {
                return BadAffinity;
            }
        }
    }



